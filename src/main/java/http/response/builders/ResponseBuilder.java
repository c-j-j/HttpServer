package http.response.builders;

import com.google.common.io.ByteSource;
import http.request.ContentType;
import http.request.HTTPAction;
import http.response.HTTPStatusCode;
import http.response.Response;

public class ResponseBuilder {
    private HTTPStatusCode statusCode = HTTPStatusCode.OK;
    private ByteSource content;
    private String location;
    private ContentType contentType;
    private HTTPAction[] allowedActions;
    private long contentLength;

    public ResponseBuilder withStatusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withContent(String content) {
        return withContent(ByteSource.wrap(content.getBytes()));
    }

    public ResponseBuilder withContent(ByteSource content) {
        this.content = content;
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

    public ResponseBuilder withContentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public Response build() {
        return new Response(statusCode, location, content, contentType, allowedActions);
    }
}
