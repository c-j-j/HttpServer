package http.config.builders;

import com.google.common.collect.Lists;
import http.config.Configuration;
import http.request.auth.Credential;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigurationBuilder {

    private List<String> protectedPaths = new ArrayList<>();
    private Optional<Credential> credential;
    private int port;
    private File baseDirectory;

    public ConfigurationBuilder withProtectedPaths(String... protectedPaths) {
        this.protectedPaths = Lists.newArrayList(protectedPaths);
        return this;
    }

    public ConfigurationBuilder withCredential(String username, String password) {
        this.credential = Optional.of(new Credential(username, password));
        return this;
    }

    public ConfigurationBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public ConfigurationBuilder withBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    public Configuration build() {
        return new Configuration(baseDirectory, port, protectedPaths, credential);
    }

}
