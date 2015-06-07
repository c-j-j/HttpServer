package http.request;

import http.HTTPAction;
import http.RequestHeader;

public class Request {
    private final RequestHeader header;

    public Request(RequestHeader header) {
        this.header = header;
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
        return header.getPayload();
    }
}
