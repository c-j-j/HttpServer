package http.request.handlers;

import http.request.Request;
import http.request.auth.AuthenticationHeader;
import http.request.auth.Authenticator;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.response.builders.ResponseBuilder;

import java.util.function.Function;

public class AuthenticationRequestWrapper implements Function<Request, Response> {

    private final Function<Request, Response> responseResolver;
    private final Authenticator authenticator;

    public AuthenticationRequestWrapper(Function<Request, Response> responseResolver, Authenticator authenticator) {
        this.responseResolver = responseResolver;
        this.authenticator = authenticator;
    }

    @Override
    public Response apply(Request request) {
        if (isAuthenticationRequired(request)) {
            return authenticateRequest(request);
        } else {
            return authenticatedResponse(request);
        }
    }

    private Response authenticateRequest(Request request) {
        if (isAuthenticationHeaderPresent(request)) {
            return checkAuthenticationHeader(request);
        } else {
            return unauthorizedResponse();
        }
    }

    private Response checkAuthenticationHeader(Request request) {
        if (checkCredentials(request.getHeader().getAuthenticationHeader().get())) {
            return authenticatedResponse(request);
        } else {
            return unauthorizedResponse();
        }
    }

    private boolean checkCredentials(AuthenticationHeader authenticationHeader) {
        return authenticator.validateCredentials(authenticationHeader.getDecodedUsername(), authenticationHeader.getDecodedPassword());
    }

    private Response authenticatedResponse(Request request) {
        return responseResolver.apply(request);
    }

    private Response unauthorizedResponse() {
        return new ResponseBuilder()
                .withBody("Authentication required")
                .withStatusCode(HTTPStatusCode.UNAUTHORIZED).build();
    }

    private boolean isAuthenticationHeaderPresent(Request request) {
        return request.getHeader().getAuthenticationHeader().isPresent();
    }

    private boolean isAuthenticationRequired(Request request) {
        return authenticator.isPathProtected(request.getPath());
    }
}
