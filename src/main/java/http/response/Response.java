package http.response;

import com.google.common.io.ByteSource;
import http.request.ContentType;
import http.request.HTTPAction;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class Response {
    private final String location;
    private final ByteSource contents;
    private final HTTPStatusCode statusCode;
    private final ContentType contentType;
    private final HTTPAction[] allowedActions;

    public Response(HTTPStatusCode statusCode, String location, ByteSource contents, ContentType contentType, HTTPAction[] allowedActions) {
        this.location = location;
        this.contents = Optional.ofNullable(contents).orElse(ByteSource.empty());
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.allowedActions = allowedActions;
    }

    public String getContentsAsString() {
        try {
            return new String(contents.read());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ByteSource getContents(){
        return contents;
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
            return contents.size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
