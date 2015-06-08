package http.request;

import http.request.handlers.file.*;

public enum HTTPAction {
    POST(new PostFileRequestHandler()),
    PUT(new PutFileFileRequestHandler()),
    HEAD(new GetFileRequestHandler()),
    PATCH(new PatchFileRequestHandler()),
    OPTIONS(new OptionFileRequestHandler()),
    DELETE(new GetFileRequestHandler()),
    GET(new GetFileRequestHandler());

    private FileRequestHandler responseHandler;

    HTTPAction(FileRequestHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public FileRequestHandler getFileResponseHandler() {
        return responseHandler;
    }
}
