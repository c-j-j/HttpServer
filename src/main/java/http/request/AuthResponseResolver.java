package http.request;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;
import http.auth.AuthenticationHeader;

import java.util.function.Function;

public class AuthResponseResolver implements Function<RequestHeader, Response> {

    private final Function<RequestHeader, Response> responseResolver;

    public AuthResponseResolver(Function<RequestHeader, Response> responseResolver) {
        this.responseResolver = responseResolver;
    }

    @Override
    public Response apply(RequestHeader requestHeader) {
        if (isAuthenticationRequired(requestHeader)) {
            return authenticateRequest(requestHeader);
        } else {
            return authenticatedResponse(requestHeader);
        }
    }

    private Response authenticateRequest(RequestHeader requestHeader) {
        if (isAuthenticationHeaderPresent(requestHeader)) {
            return checkAuthenticationHeader(requestHeader);
        } else {
            return unauthorizedResponse();
        }
    }

    private Response checkAuthenticationHeader(RequestHeader requestHeader) {
        if (checkCredentials(requestHeader.getAuthenticationHeader().get())) {
            return authenticatedResponse(requestHeader);
        } else {
            return unauthorizedResponse();
        }
    }

    private boolean checkCredentials(AuthenticationHeader authenticationHeader) {
        return authenticationHeader.getDecodedUsername().equals("admin") && authenticationHeader.getDecodedPassword().equals("hunter2");
    }

    private Response authenticatedResponse(RequestHeader requestHeader) {
        return responseResolver.apply(requestHeader);
    }

    private Response unauthorizedResponse() {
        return new ResponseBuilder()
                .withContent("Authentication required")
                .withStatusCode(HTTPStatusCode.UNAUTHORIZED).build();
    }

    private boolean isAuthenticationHeaderPresent(RequestHeader requestHeader) {
        return requestHeader.getAuthenticationHeader().isPresent();
    }

    private boolean isAuthenticationRequired(RequestHeader requestHeader) {
        return requestHeader.getPath().equals("/logs");
    }
}
