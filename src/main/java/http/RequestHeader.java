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
    private final long contentLength;
    private Optional<String> ifMatchValue;

    public RequestHeader(HTTPAction httpAction, String path, Optional<AuthenticationHeader> authenticationHeader, String requestPayload, long contentLength, Optional<String> ifMatchValue) {
        this.httpAction = httpAction;
        this.path = path;
        this.authenticationHeader = authenticationHeader;
        this.requestPayload = requestPayload;
        this.contentLength = contentLength;
        this.ifMatchValue = ifMatchValue;
    }

    public HTTPAction getAction() {
        return httpAction;
    }

    public String getPath() {
        return path;
    }

    public Response fileBasedResponse(File baseFolder, Request request) {
        return httpAction.getFileResponseHandler().getResponse(baseFolder, request);
    }

    public Optional<AuthenticationHeader> getAuthenticationHeader() {
        return authenticationHeader;
    }

    public Optional<String> getIfMatchValue() {
        return ifMatchValue;
    }

    public String getPayload() {
        return requestPayload;
    }

    public long getContentLength() {
        return contentLength;
    }

}
