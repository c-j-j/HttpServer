package http.request;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;
import http.auth.AuthenticationHeader;
import http.fakes.SpyFunction;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthResponseResolverTest {

    private SpyFunction<RequestHeader, Response> wrappedResponseResolver;
    private Response wrappedResponse;

    @Before
    public void setUp() throws Exception {
        wrappedResponse = new ResponseBuilder().build();
        wrappedResponseResolver = new SpyFunction<>(wrappedResponse);
    }

    @Test
    public void callsWrappedResponseResolver() {
        RequestHeader requestHeader = new RequestHeaderBuilder().withPath("/").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(requestHeader);
        assertThat(wrappedResponseResolver.wasCalledWith()).isEqualTo(requestHeader);
        assertThat(response).isEqualTo(wrappedResponse);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void blocksAccessToProtectedPath(){
        RequestHeader requestHeader = new RequestHeaderBuilder().withPath("/logs").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(requestHeader);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.UNAUTHORIZED);
        assertThat(response.getContentsAsString()).isEqualTo("Authentication required");
    }

    @Test
    public void allowsAccessToProtectedPath(){
        RequestHeader requestHeader = new RequestHeaderBuilder().withAuthenticationHeader(new AuthenticationHeader(encode("admin:hunter2"))).withPath("/logs").build();
        Response response = new AuthResponseResolver(wrappedResponseResolver).apply(requestHeader);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    private String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }
}