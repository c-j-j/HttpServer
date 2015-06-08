package http.request.builder;

import http.request.RequestHeader;
import http.request.Request;

public class RequestBuilder {
    private RequestHeader header;
    private String body;

    public RequestBuilder withHeader(RequestHeader header){
        this.header = header;
        return this;
    }

    public RequestBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public Request build() {
        return new Request(header, body);
    }
}
