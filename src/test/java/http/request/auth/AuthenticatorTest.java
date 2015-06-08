package http.request.auth;

import http.config.Configuration;
import http.config.builders.ConfigurationBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticatorTest {

    @Test
    public void pathNotProtected() {
        assertThat(new Authenticator(new ConfigurationBuilder().build()).isPathProtected("/unprotectedPath")).isFalse();
    }

    @Test
    public void pathProtected() {
        Configuration configuration = new ConfigurationBuilder().withProtectedPaths("/protectedPath").build();
        assertThat(new Authenticator(configuration).isPathProtected("/protectedPath")).isTrue();
    }

    @Test
    public void userCredentialsInvalid(){
        Configuration configuration = new ConfigurationBuilder().withCredential("username", "password").build();
        assertThat(new Authenticator(configuration).validateCredentials("username", "password")).isTrue();
    }
}