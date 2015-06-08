package http.config;

import http.request.auth.Credential;

import java.util.List;
import java.util.Optional;

public class Configuration {

    private final List<String> protectedPaths;
    private Optional<Credential> credential;

    public Configuration(List<String> protectedPaths, Optional<Credential> credential) {
        this.protectedPaths = protectedPaths;
        this.credential = credential;
    }

    public List<String> getProtectedPaths() {
        return protectedPaths;
    }

    public Optional<Credential> getCredential() {
        return credential;
    }
}
