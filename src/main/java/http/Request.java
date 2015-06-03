package http;

import java.io.File;

public class Request {
    private final HTTPAction httpAction;
    private final String path;

    public Request(HTTPAction httpAction, String path) {
        this.httpAction = httpAction;
        this.path = path;
    }

    public HTTPAction getAction() {
        return httpAction;
    }

    public String getPath() {
        return path;
    }

    public Response getResponse(File baseFolder, Request request) {
        return httpAction.getResponseHandler().getResponse(baseFolder, request);
    }
}
