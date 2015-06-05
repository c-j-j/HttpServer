package http.builders;

import http.HTTPAction;
import http.auth.AuthenticationHeader;

public class HTTPRequestBuilder {

    private HTTPAction action = HTTPAction.GET;
    private String path;
    private AuthenticationHeader authenticationHeader;

    public HTTPRequestBuilder withAction(HTTPAction action) {
        this.action = action;
        return this;
    }

    public HTTPRequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequestBuilder withAuthentication(AuthenticationHeader authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header());
        if (authenticationHeader != null) {
            stringBuilder.append(authenticationLine());
        }

        return stringBuilder.append("\n").toString();
    }

    private String authenticationLine() {
        return String.format("Authorization: %s %s\n",
                "Basic", authenticationHeader.getAuthValue());
    }

    private String header() {
        return String.format("%s %s HTTP/1.1\n", action, path);
    }

}
