public class Request {
    private final HTTPAction httpAction;
    private final String path;

    public Request(HTTPAction httpAction, String path) {
        this.httpAction = httpAction;
        this.path = path;
    }

    public Request() {
        this(HTTPAction.GET, "/");
    }

    public HTTPAction getAction() {
        return httpAction;
    }

    public String getPath() {
        return path;
    }
}
