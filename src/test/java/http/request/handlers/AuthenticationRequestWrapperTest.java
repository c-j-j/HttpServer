package http.request.handlers;

import http.request.auth.Authenticator;
import http.request.builder.RequestHeaderBuilder;
import http.response.builders.ResponseBuilder;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.auth.AuthenticationHeader;
import http.fakes.SpyFunction;
import http.request.Request;
import http.request.RequestHeader;
import http.request.builder.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationRequestWrapperTest {

    private SpyFunction<Request, Response> wrappedResponseResolver;
    private Response wrappedResponse;
    private FakeAuthenticator fakeAuthenticator = new FakeAuthenticator();

    @Before
    public void setUp() throws Exception {
        wrappedResponse = new ResponseBuilder().build();
        wrappedResponseResolver = new SpyFunction<>(wrappedResponse);
    }

    @Test
    public void callsWrappedResponseResolver() {
        Request request = buildRequest("/");
        fakeAuthenticator.allPathsUnprotected();
        Response response = new AuthenticationRequestWrapper(wrappedResponseResolver, fakeAuthenticator).apply(request);
        assertThat(wrappedResponseResolver.wasCalledWith()).isEqualTo(request);
        assertThat(response).isEqualTo(wrappedResponse);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void blocksAccessToProtectedPath() {
        Request request = buildRequest("/logs");
        fakeAuthenticator.allPathsProtected();
        fakeAuthenticator.allCredentailsInvalid();
        Response response = new AuthenticationRequestWrapper(wrappedResponseResolver, fakeAuthenticator).apply(request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.UNAUTHORIZED);
        assertThat(response.getBodyAsString()).isEqualTo("Authentication required");
    }

    @Test
    public void allowsAccessToProtectedPath() {
        RequestHeader requestHeader = new RequestHeaderBuilder().withAuthenticationHeader(new AuthenticationHeader(encode("admin:hunter2"))).withURI("/logs").build();
        fakeAuthenticator.allPathsUnprotected();
        fakeAuthenticator.allCredentailsValid();
        Request request = new RequestBuilder().withHeader(requestHeader).build();
        Response response = new AuthenticationRequestWrapper(wrappedResponseResolver, fakeAuthenticator).apply(request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    private Request buildRequest(String path) {
        return new RequestBuilder().withHeader(new RequestHeaderBuilder().withURI(path).build()).build();
    }

    private String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }

    private class FakeAuthenticator extends Authenticator {

        private boolean pathsProtected;
        private boolean credentailsValid;

        public FakeAuthenticator() {
            super(null);
        }

        public void allPathsUnprotected() {
            pathsProtected = false;
        }

        public void allPathsProtected() {
            pathsProtected = true;
        }

        public void allCredentailsInvalid() {
            credentailsValid = false;
        }

        public void allCredentailsValid() {
            credentailsValid = true;
        }

        @Override
        public boolean isPathProtected(String path) {
            return pathsProtected;
        }

        @Override
        public boolean validateCredentials(String username, String password){
           return credentailsValid;
        }

    }
}