package builders;

import http.HTTPStatusCode;
import http.Response;

public class ResponseBuilder {
    private HTTPStatusCode statusCode = HTTPStatusCode.OK;
    private String content;

    public ResponseBuilder withStatusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public Response build() {
        return new Response(statusCode, content);
    }
}
