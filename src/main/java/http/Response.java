package http;

import com.google.common.io.CharSource;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Response {
    private final String location;
    private final CharSource contents;
    private final HTTPStatusCode statusCode;
    private final ContentType contentType;

    public Response(HTTPStatusCode statusCode, String location, CharSource contents, ContentType contentType) {
        this.location = location;
        this.contents = contents;
        this.statusCode = statusCode;
        this.contentType = contentType;
    }

    public String getContentsAsString() {
        try {
            return contents != null ? contents.read() : "";
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
}
