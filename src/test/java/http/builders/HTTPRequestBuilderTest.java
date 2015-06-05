package http.builders;

import http.auth.AuthenticationHeader;
import http.auth.AuthenticationType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HTTPRequestBuilderTest {

    @Test
    public void hasAuthenticationLine(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader(AuthenticationType.BASIC, "authValue");
        String requestPayload = new HTTPRequestBuilder()
                .withAuthentication(authenticationHeader)
                .build();

        assertThat(requestPayload).contains(String.format("Authentication: %s %s",
                    authenticationHeader.getType(), authenticationHeader.getAuthValue()));
    }
}
