package builders;

import http.HTTPAction;
import http.Request;
import http.auth.AuthenticationHeader;

import java.util.Optional;

public class RequestBuilder {
    private String path;
    private HTTPAction httpAction = HTTPAction.GET;
    private Optional<AuthenticationHeader> authenticationHeader = Optional.<AuthenticationHeader>empty();
    private String requestPayload;

    public RequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder withHTTPAction(HTTPAction httpAction) {
        this.httpAction = httpAction;
        return this;
    }

    public RequestBuilder withAuthenticationHeader(Optional<AuthenticationHeader> authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
        return this;
    }

    public RequestBuilder withAuthenticationHeader(AuthenticationHeader authenticationHeader) {
        return withAuthenticationHeader(Optional.of(authenticationHeader));
    }

    public RequestBuilder withRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
        return this;
    }

    public Request build() {
        return new Request(httpAction, path, authenticationHeader, requestPayload);
    }

}
