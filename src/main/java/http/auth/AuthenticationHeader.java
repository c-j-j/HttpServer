package http.auth;

public class AuthenticationHeader {
    private final AuthenticationType type;
    private final String authValue;

    public AuthenticationHeader(AuthenticationType type, String authValue) {

        this.type = type;
        this.authValue = authValue;
    }

    public AuthenticationType getType() {
        return type;
    }

    public String getAuthValue() {
        return authValue;
    }
}
