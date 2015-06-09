package http.config;

import http.request.auth.Credential;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Configuration {

    private final File baseDirectory;
    private final int port;
    private final List<String> protectedPaths;
    private final Optional<Credential> credential;

    public Configuration(File baseDirectory, int port, List<String> protectedPaths, Optional<Credential> credential) {
        this.baseDirectory = baseDirectory;
        this.port = port;
        this.protectedPaths = protectedPaths;
        this.credential = credential;
    }

    public List<String> getProtectedPaths() {
        return protectedPaths;
    }

    public Optional<Credential> getCredential() {
        return credential;
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }

    public int getPort() {
        return port;
    }
}
