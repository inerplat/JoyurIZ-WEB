package inerplat.joyuriz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${web.ip}") private String webIp;
    @Value("${web.port}") private String webPort;
    @Value("${web.method}") private String webMethod;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // log.info(String.format("%s://%s:%s", webMethod, webIp, webPort));
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST");
    }
}