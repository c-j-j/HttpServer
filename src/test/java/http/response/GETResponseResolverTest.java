package http.response;

import builders.RequestBuilder;
import http.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GETResponseResolverTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File baseFolder;
    private Request request;

    @Before
    public void setUp() throws Exception {
        baseFolder = temporaryFolder.newFolder();
        request = new RequestBuilder().withPath("/filename").build();
    }

    @Test
    public void fileContents() throws IOException {
        String fileContent = "Hello, World";
        writeToFile("filename", fileContent);
        Response response = new GETResponseResolver().getResponse(baseFolder, request);
        assertThat(response.getContentsAsString()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void yields404ResponseWhenFileDoesNotExist() {
        Response response = new GETResponseResolver().getResponse(baseFolder, request);
        assertThat(response.getContentsAsString()).isEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.NOT_FOUND);
    }

    private void writeToFile(String fileName, String fileContents) throws IOException {
        File requestedFile = new File(baseFolder, fileName);
        FileUtils.writeStringToFile(requestedFile, fileContents);
    }

}