package http.request;

import builders.RequestBuilder;
import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.Request;
import http.Response;
import http.auth.AuthenticationHeader;
import http.fakes.TestFunction;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthResponseResolverTest {

    private TestFunction<Request, Response> wrappedResponseResolver;
    private Response wrappedResponse;

    @Before
    public void setUp() throws Exception {
        wrappedResponse = new ResponseBuilder().build();
        wrappedResponseResolver = new TestFunction<>(wrappedResponse);
    }

    @Test
    public void callsWrappedResponseResolver() {
        Request request = new RequestBuilder().withPath("/").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(request);
        assertThat(wrappedResponseResolver.wasCalledWith()).isEqualTo(request);
        assertThat(response).isEqualTo(wrappedResponse);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void blocksAccessToProtectedPath(){
        Request request = new RequestBuilder().withPath("/logs").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.UNAUTHORIZED);
        assertThat(response.getContentsAsString()).isEqualTo("Authentication required");
    }

    @Test
    public void allowsAccessToProtectedPath(){
        Request request = new RequestBuilder().withAuthenticationHeader(new AuthenticationHeader(encode("admin:hunter2"))).withPath("/logs").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    private String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }
}