package http;

import com.google.common.collect.Sets;
import http.config.Configuration;
import http.config.builders.ConfigurationBuilder;
import http.launcher.ArgumentParser;
import http.resource.Resource;
import http.resource.resources.FormResource;
import http.resource.resources.ParameterResource;
import http.resource.resources.RedirectResource;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        new HttpServer(setupResources(), buildConfiguration(new ArgumentParser(args))).start();
    }

    private static HashSet<Resource> setupResources() {
        return Sets.newHashSet(new RedirectResource(), new FormResource(), new ParameterResource());
    }

    private static Configuration buildConfiguration(ArgumentParser argumentParser) {
        return new ConfigurationBuilder()
                .withPort(getPortFromArgs(argumentParser))
                .withBaseDirectory(getPublicDirectoryFromArgs(argumentParser))
                .withProtectedPaths("/logs")
                .withCredential("admin", "hunter2")
                .build();
    }

    private static File getPublicDirectoryFromArgs(ArgumentParser argumentParser) {
        try {
            return new File(argumentParser.getArgument("-d").orElse(System.getenv("PUBLIC_DIR")));
        }catch(RuntimeException e){
            System.out.println("No public directory specified. Either use -d argument or set PUBLIC_DIR environment variable");
            throw e;
        }
    }

    private static Integer getPortFromArgs(ArgumentParser argumentParser) {
        return Integer.valueOf(argumentParser.getArgument("-p").orElse("5000"));
    }
}
