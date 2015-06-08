package http.request.auth;

import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationHeaderTest {

    private String encodedCredentials;
    private String username;
    private String password;

    @Before
    public void setUp() throws Exception {
        username = "username";
        password = "password";
        encodedCredentials = encode(username + ":" + password);
    }

    private String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }

    @Test
    public void getsDecodedUsername(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader(encodedCredentials);
        assertThat(authenticationHeader.getDecodedUsername()).isEqualTo(username);
    }

    @Test
    public void getsDecodedPassword(){
        AuthenticationHeader authenticationHeader = new AuthenticationHeader(encodedCredentials);
        assertThat(authenticationHeader.getDecodedPassword()).isEqualTo(password);
    }
}