package inerplat.joyuriz.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.composite.AbstractPatternJsonProvider;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import net.logstash.logback.marker.Markers;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Component
public class RequestFilter implements Filter {
    LoggingEventCompositeJsonEncoder loggingEventCompositeJsonEncoder = new LoggingEventCompositeJsonEncoder();
    private static final Marker MESSAGE_MARKER = MarkerFactory.getMarker("test");

    private AbstractPatternJsonProvider<ILoggingEvent> withPattern(String pattern,
                                                                   AbstractPatternJsonProvider<ILoggingEvent> provider) {
        provider.setPattern(pattern);
        return provider;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("id", UUID.randomUUID().toString());
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        long end = System.currentTimeMillis();

        long start = System.currentTimeMillis();
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            MDC.remove("id");
        }

        RequestLogFormat rq = new RequestLogFormat(
                ((HttpServletRequest) request).getMethod(),
                ((HttpServletRequest) request).getRequestURI(),
                responseWrapper.getStatus(),
                (end - start) / 1000.0,
                getRequestBody(requestWrapper)
        );
        Marker logMarker = Markers.append("rq", rq)
                .and(Markers.append("hd", getHeaders((HttpServletRequest) request)))
                .and(Markers.append("rs", getResponseBody(responseWrapper)));

        log.info(logMarker, "HttpLog");
    }

    private Object getHeaders(HttpServletRequest request) {
        Map<Object, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    return String.format("{\"error\": %s", e.toString());
                }
            }
        }
        return " - ";
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return null == payload ? " - " : payload;
    }
}