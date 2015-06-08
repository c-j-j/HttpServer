package http.response.builders;

import com.google.common.io.ByteSource;
import http.request.ContentType;
import http.request.HTTPAction;
import http.response.HTTPStatusCode;
import http.response.Response;

public class ResponseBuilder {
    private HTTPStatusCode statusCode = HTTPStatusCode.OK;
    private ByteSource body;
    private String location;
    private ContentType contentType;
    private HTTPAction[] allowedActions;

    public ResponseBuilder withStatusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withBody(String content) {
        return withBody(ByteSource.wrap(content.getBytes()));
    }

    public ResponseBuilder withBody(ByteSource content) {
        this.body = content;
        return this;
    }

    public ResponseBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public ResponseBuilder withContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public ResponseBuilder withAllowedOptions(HTTPAction... actions) {
        this.allowedActions = actions;
        return this;
    }

    public Response build() {
        return new Response(statusCode, location, body, contentType, allowedActions);
    }
}
