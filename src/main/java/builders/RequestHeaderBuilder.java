package builders;

import http.HTTPAction;
import http.RequestHeader;
import http.auth.AuthenticationHeader;

import java.util.Optional;

public class RequestHeaderBuilder {
    private String path;
    private HTTPAction httpAction = HTTPAction.GET;
    private Optional<AuthenticationHeader> authenticationHeader = Optional.<AuthenticationHeader>empty();
    private String requestPayload;
    private long contentLength;
    private Optional<String> ifMatchValue;

    public RequestHeaderBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public RequestHeaderBuilder withHTTPAction(HTTPAction httpAction) {
        this.httpAction = httpAction;
        return this;
    }

    public RequestHeaderBuilder withAuthenticationHeader(Optional<AuthenticationHeader> authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
        return this;
    }

    public RequestHeaderBuilder withAuthenticationHeader(AuthenticationHeader authenticationHeader) {
        return withAuthenticationHeader(Optional.of(authenticationHeader));
    }

    public RequestHeaderBuilder withRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
        return this;
    }

    public RequestHeaderBuilder withContentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public RequestHeaderBuilder withIfMatchValue(Optional<String> ifMatch) {
        this.ifMatchValue = ifMatch;
        return this;
    }

    public RequestHeader build() {
        return new RequestHeader(httpAction, path, authenticationHeader, requestPayload, contentLength, ifMatchValue);
    }

}
