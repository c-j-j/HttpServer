package http;

public class Response {
    private final String location;
    private final String contents;
    private final HTTPStatusCode statusCode;

    public Response(HTTPStatusCode statusCode, String location, String contents) {
        this.location = location;
        this.contents = contents;
        this.statusCode = statusCode;
    }

    public String getContents() {
        return contents;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public String getLocation() {
        return location;
    }
}
