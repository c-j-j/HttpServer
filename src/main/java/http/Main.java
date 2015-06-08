package http;

import com.google.common.collect.Sets;
import http.config.Configuration;
import http.config.builders.ConfigurationBuilder;
import http.resource.Resource;
import http.resource.resources.FormResource;
import http.resource.resources.ParameterResource;
import http.resource.resources.RedirectResource;

import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new ConfigurationBuilder()
                .withProtectedPaths("/logs")
                .withCredential("admin", "hunter2")
                .build();
        Set<Resource> resources = Sets.newHashSet(new RedirectResource(), new FormResource(), new ParameterResource());
        new HttpServer(resources, new File("/Users/chrisjordan/java_projects/cob_spec/public/"), 5000, configuration).start();
    }
}
