package http.request.auth;

import http.config.Configuration;

public class Authenticator {
    private final Configuration configuration;

    public Authenticator(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean isPathProtected(String path) {
        return configuration.getProtectedPaths().contains(path);
    }

    public boolean validateCredentials(String username, String password) {
        return configuration.getCredential().isPresent() && configuration.getCredential().get().validate(username, password);
    }
}
