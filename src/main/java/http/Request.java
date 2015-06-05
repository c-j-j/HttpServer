package http;

import http.auth.AuthenticationHeader;

import java.io.File;
import java.util.Optional;

public class Request {
    private final HTTPAction httpAction;
    private final String path;
    private Optional<AuthenticationHeader> authenticationHeader;

    public Request(HTTPAction httpAction, String path, Optional<AuthenticationHeader> authenticationHeader) {
        this.httpAction = httpAction;
        this.path = path;
        this.authenticationHeader = authenticationHeader;
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
}
