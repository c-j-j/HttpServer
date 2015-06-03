package http;

public class Response {
    private final String contents;
    private final HTTPStatusCode statusCode;

    public Response(HTTPStatusCode statusCode, String contents) {
        this.contents = contents;
        this.statusCode = statusCode;
    }

    public String getContents() {
        return contents;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }
}
