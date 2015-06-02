public class Request {
    private final HTTPAction httpAction;

    public Request(HTTPAction httpAction) {
        this.httpAction = httpAction;
    }

    public Request() {
        this(HTTPAction.GET);
    }

    public HTTPAction getAction() {
        return httpAction;
    }
}
