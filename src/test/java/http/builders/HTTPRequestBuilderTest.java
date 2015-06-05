package http.builders;

import http.auth.AuthenticationHeader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HTTPRequestBuilderTest {

    @Test
    public void hasAuthenticationLine(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader("authValue");
        String requestPayload = new HTTPRequestBuilder()
                .withAuthentication(authenticationHeader)
                .build();

        assertThat(requestPayload).contains(String.format("Authorization: %s %s",
                    "Basic", authenticationHeader.getAuthValue()));
    }
}
