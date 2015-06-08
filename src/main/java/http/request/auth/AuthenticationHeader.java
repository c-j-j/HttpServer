package http.request.auth;

import java.util.Base64;

public class AuthenticationHeader {
    private final String authValue;

    public AuthenticationHeader(String authValue) {

        this.authValue = authValue;
    }

    public String getAuthValue() {
        return authValue;
    }

    public String getDecodedUsername() {
        return splitCredentialString(decode(authValue))[0];
    }

    public String getDecodedPassword() {
        return splitCredentialString(decode(authValue))[1];
    }

    private String[] splitCredentialString(String decodedCredentials) {
        return decodedCredentials.split(":");
    }

    private String decode(String s) {
        return new String(Base64.getDecoder().decode(s.getBytes()));
    }
}
