package http;

import http.response.*;

public enum HTTPAction {
    POST(new POSTResponseResolver()),
    PUT(new PUTResponseResolver()),
    HEAD(new GETResponseResolver()), 
    PATCH(new GETResponseResolver()),
    OPTIONS(new OPTIONResponseResolver()),
    DELETE(new GETResponseResolver()), 
    GET(new GETResponseResolver());

    private ResponseResolver responseHandler;

    HTTPAction(ResponseResolver responseHandler) {
        this.responseHandler = responseHandler;
    }

    public ResponseResolver getResponseHandler() {
        return responseHandler;
    }
}
