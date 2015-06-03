package http;

import http.response.GETResponseResolver;
import http.response.ResponseResolver;

public enum HTTPAction {
    POST(new GETResponseResolver()),
    PUT(new GETResponseResolver()), 
    HEAD(new GETResponseResolver()), 
    PATCH(new GETResponseResolver()), 
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
