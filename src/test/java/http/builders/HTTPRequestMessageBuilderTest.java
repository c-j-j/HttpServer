package http.builders;

import http.auth.AuthenticationHeader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HTTPRequestMessageBuilderTest {


    @Test
    public void hasAuthenticationLine(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader("authValue");
        String requestPayload = new HTTPRequestMessageBuilder()
                .withAuthentication(authenticationHeader)
                .build();

        assertThat(requestPayload).contains(String.format(HTTPRequestMessageBuilder.AUTHORIZATION_TEMPLATE,
                    "Basic", authenticationHeader.getAuthValue()));
    }
    
    @Test
    public void hasIfMatchLine(){
        String ifMatchValue = "ifMatchValue";
        String requestText = new HTTPRequestMessageBuilder().withIfMatch(ifMatchValue).build();
        assertThat(requestText).contains(String.format(HTTPRequestMessageBuilder.IF_MATCH_TEMPLATE,
                ifMatchValue));
    }
}
