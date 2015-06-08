package http;

import com.google.common.collect.Sets;
import http.resource.resources.FormResource;
import http.resource.resources.ParameterResource;
import http.resource.resources.RedirectResource;
import http.resource.Resource;

import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Resource> resources = Sets.newHashSet(new RedirectResource(), new FormResource(), new ParameterResource());
        new HttpServer(resources, new File("/Users/chrisjordan/java_projects/cob_spec/public/"), 5000).start();
    }
}
