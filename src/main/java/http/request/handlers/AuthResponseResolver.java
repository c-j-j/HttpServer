package http.request.handlers;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.Response;
import http.auth.AuthenticationHeader;
import http.request.Request;

import java.util.function.Function;

public class AuthResponseResolver implements Function<Request, Response> {

    private final Function<Request, Response> responseResolver;

    public AuthResponseResolver(Function<Request, Response> responseResolver) {
        this.responseResolver = responseResolver;
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
        return authenticationHeader.getDecodedUsername().equals("admin") && authenticationHeader.getDecodedPassword().equals("hunter2");
    }

    private Response authenticatedResponse(Request request) {
        return responseResolver.apply(request);
    }

    private Response unauthorizedResponse() {
        return new ResponseBuilder()
                .withContent("Authentication required")
                .withStatusCode(HTTPStatusCode.UNAUTHORIZED).build();
    }

    private boolean isAuthenticationHeaderPresent(Request request) {
        return request.getHeader().getAuthenticationHeader().isPresent();
    }

    private boolean isAuthenticationRequired(Request request) {
        return request.getPath().equals("/logs");
    }
}
