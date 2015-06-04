package builders;

import http.HTTPAction;
import http.Request;

public class RequestBuilder {
    private String path;
    private HTTPAction httpAction = HTTPAction.GET;

    public RequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder withHTTPAction(HTTPAction httpAction) {
        this.httpAction = httpAction;
        return this;
    }

    public Request build() {
        return new Request(httpAction, path);
    }

}
