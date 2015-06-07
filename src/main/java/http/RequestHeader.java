package http;

import http.auth.AuthenticationHeader;
import http.request.Request;

import java.io.File;
import java.util.Optional;

public class RequestHeader {
    private final HTTPAction httpAction;
    private final String path;
    private final Optional<AuthenticationHeader> authenticationHeader;
    private final String requestPayload;

    public RequestHeader(HTTPAction httpAction, String path, Optional<AuthenticationHeader> authenticationHeader, String requestPayload) {
        this.httpAction = httpAction;
        this.path = path;
        this.authenticationHeader = authenticationHeader;
        this.requestPayload = requestPayload;
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

    public Optional<AuthenticationHeader> getAuthenticationHeader() {
        return authenticationHeader;
    }

    public String getPayload() {
        return requestPayload;
    }
}
