package http.request.builder;

import http.RequestHeader;
import http.request.Request;

public class RequestBuilder {
    private RequestHeader header;

    public RequestBuilder withHeader(RequestHeader header){
        this.header = header;
        return this;
    }

    public Request build() {
        return new Request(header);
    }
}
