package http;

import http.response.*;
import http.response.file.PatchFileResponseHandler;

public enum HTTPAction {
    POST(new PostFileResponseHandler()),
    PUT(new PutFileFileResponseHandler()),
    HEAD(new GetFileResponseHandler()),
    PATCH(new PatchFileResponseHandler()),
    OPTIONS(new OptionFileResponseHandler()),
    DELETE(new GetFileResponseHandler()),
    GET(new GetFileResponseHandler());

    private FileResponseHandler responseHandler;

    HTTPAction(FileResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public FileResponseHandler getFileResponseHandler() {
        return responseHandler;
    }
}
