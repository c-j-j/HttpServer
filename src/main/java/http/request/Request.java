package http.request;

import http.HTTPAction;

import java.util.Optional;

public class Request {
    private final RequestHeader header;
    private final String body;

    public Request(RequestHeader header, String body) {
        this.header = header;
        this.body = body;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public String getPath() {
        return header.getPath();
    }

    public HTTPAction getAction() {
        return header.getAction();
    }

    public String getPayload() {
        return header.getPayload() + body;
    }

    public String getBody() {
        return body;
    }

    public Optional<String> getIfMatchValue() {
        return header.getIfMatchValue();
    }
}
