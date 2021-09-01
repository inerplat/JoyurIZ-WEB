package inerplat.joyuriz.log;

import lombok.Data;

@Data
public
class RequestLogFormat{
    String method;
    String uri;
    int status;
    double runtime;
    String body;

    public RequestLogFormat(String method, String requestURI, int status, double t, String body) {
        this.method = method;
        this.uri = requestURI;
        this.status = status;
        this.runtime = t;
        this.body = body;
    }
}