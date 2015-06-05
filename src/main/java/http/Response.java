package http;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

import java.io.IOException;

public class Response {
    private final String location;
    private final ByteSource contents;
    private final HTTPStatusCode statusCode;
    private final ContentType contentType;
    private final HTTPAction[] allowedActions;
    private final long contentLength;

    public Response(HTTPStatusCode statusCode, String location, ByteSource contents, ContentType contentType, long contentLength, HTTPAction[] allowedActions) {
        this.location = location;
        this.contents = contents;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.allowedActions = allowedActions;
        this.contentLength = contentLength;
    }

    public String getContentsAsString() {
        try {
            return contents != null ? new String(contents.read()): "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ByteSource getContents(){
        if(contents==null) {
            return ByteSource.empty();
        }else{
            return contents;
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

    public HTTPAction[] getAllowedOptions() {
        return allowedActions;
    }

    public long getContentLength() {
        return contentLength;
    }
}
