package http.request.auth;

public class Credential {
    private final String username;
    private final String password;

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
