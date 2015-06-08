package http.config.builders;

import com.google.common.collect.Lists;
import http.config.Configuration;
import http.request.auth.Credential;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigurationBuilder {

    private List<String> protectedPaths = new ArrayList<>();
    private Optional<Credential> credential;

    public ConfigurationBuilder withProtectedPaths(String... protectedPaths) {
        this.protectedPaths = Lists.newArrayList(protectedPaths);
        return this;
    }

    public ConfigurationBuilder withCredential(String username, String password) {
        this.credential = Optional.of(new Credential(username, password));
        return this;
    }

    public Configuration build() {
        return new Configuration(protectedPaths, credential);
    }

}
