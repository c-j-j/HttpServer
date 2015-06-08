package http.response;

import com.google.common.io.ByteSource;
import http.request.ContentType;
import http.request.HTTPAction;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class Response {
    private final String location;
    private final ByteSource body;
    private final HTTPStatusCode statusCode;
    private final ContentType contentType;
    private final HTTPAction[] allowedActions;

    public Response(HTTPStatusCode statusCode, String location, ByteSource body, ContentType contentType, HTTPAction[] allowedActions) {
        this.location = location;
        this.body = Optional.ofNullable(body).orElse(ByteSource.empty());
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.allowedActions = allowedActions;
    }

    public String getBodyAsString() {
        try {
            return new String(body.read());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ByteSource getBody(){
        return body;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public String getLocation() {
        return location;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public HTTPAction[] getAllowedOptions() {
        return allowedActions;
    }

    public long getContentLength() {
        try {
            return body.size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
