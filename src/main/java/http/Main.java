package http;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        new HttpServer(new File("/Users/chrisjordan/java_projects/cob_spec/public/"), 5000).start();
    }
}
