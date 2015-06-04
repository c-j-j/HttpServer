package http.response;

import builders.RequestBuilder;
import http.HTTPStatusCode;
import http.Response;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PUTResponseResolverTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseDir;

    @Before
    public void setUp() throws IOException {
        baseDir = temporaryFolder.newFolder();
    }
    @Test
    public void yields200Status() {
        Response response = new PUTResponseResolver().getResponse(baseDir, new RequestBuilder().withPath("/new_file").build());
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void methodNotAllowedWhenFileExists() throws IOException {
        File existentFile = new File(baseDir, "existentFile");
        FileUtils.writeStringToFile(existentFile, "I already exist");

        Response response = new PUTResponseResolver().getResponse(baseDir, new RequestBuilder().withPath("/existentFile").build());
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.METHOD_NOT_ALLOWED);
    }
}