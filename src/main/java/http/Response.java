package http;

import com.google.common.io.CharSource;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Response {
    private final String location;
    private final CharSource contents;
    private final HTTPStatusCode statusCode;

    public Response(HTTPStatusCode statusCode, String location, CharSource contents) {
        this.location = location;
        this.contents = contents;
        this.statusCode = statusCode;
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
}
