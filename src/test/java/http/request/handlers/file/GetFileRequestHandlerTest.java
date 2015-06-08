package http.request.handlers.file;

import http.request.builder.RequestHeaderBuilder;
import http.request.Request;
import http.request.RequestHeader;
import http.request.builder.RequestBuilder;
import http.response.HTTPStatusCode;
import http.response.Response;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetFileRequestHandlerTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File baseFolder;
    private Request request;

    @Before
    public void setUp() throws Exception {
        baseFolder = temporaryFolder.newFolder();
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/filename").build();
        request = new RequestBuilder().withHeader(requestHeader).build();
    }

    @Test
    public void fileContents() throws IOException {
        String fileContent = "Hello, World";
        writeToFile("filename", fileContent);
        Response response = new GetFileRequestHandler().getResponse(baseFolder, request);
        assertThat(response.getBodyAsString()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void fileNotFound() {
        Response response = new GetFileRequestHandler().getResponse(baseFolder, request);
        assertThat(response.getBodyAsString()).isEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.NOT_FOUND);
    }

    private void writeToFile(String fileName, String fileContents) throws IOException {
        File requestedFile = new File(baseFolder, fileName);
        FileUtils.writeStringToFile(requestedFile, fileContents);
    }

}