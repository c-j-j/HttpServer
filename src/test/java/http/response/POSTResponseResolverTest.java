package http.response;

import builders.RequestHeaderBuilder;
import http.HTTPStatusCode;
import http.Response;
import http.request.Request;
import http.request.builder.RequestBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class POSTResponseResolverTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseDir;

    @Before
    public void setUp() throws IOException {
        baseDir = temporaryFolder.newFolder();
    }

    @Test
    public void yields200Status() {
        Response response = new POSTResponseResolver().getResponse(baseDir, buildRequest("/new_file"));
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    private Request buildRequest(String path) {
        return new RequestBuilder().withHeader(new RequestHeaderBuilder().withPath(path).build()).build();
    }

    @Test
    public void yieldsMethodNotAllowedWhenFileExists() throws IOException {
        File existentFile = new File(baseDir, "existentFile");
        FileUtils.writeStringToFile(existentFile, "I already exist");

        Response response = new POSTResponseResolver().getResponse(baseDir, buildRequest("/existentFile"));
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.METHOD_NOT_ALLOWED);


    }
}