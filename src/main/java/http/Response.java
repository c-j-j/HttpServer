package http;

public class Response {
    private final String contents;

    public Response(String contents) {
        this.contents = contents;
    }

    public Response() {
        this("");
    }

    public String getContents() {
        return contents;
    }
}
