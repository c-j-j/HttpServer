package builders;

import http.HTTPAction;
import http.Request;

public class RequestBuilder {
    private String path;

    public RequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public Request build() {
        return new Request(HTTPAction.GET, path);
    }
}
