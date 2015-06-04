package builders;

import http.HTTPStatusCode;
import http.Response;

public class ResponseBuilder {
    private HTTPStatusCode statusCode = HTTPStatusCode.OK;
    private String content;
    private String location;

    public ResponseBuilder withStatusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public ResponseBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public Response build() {
        return new Response(statusCode, location, content);
    }

}
