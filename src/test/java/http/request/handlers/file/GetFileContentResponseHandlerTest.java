package http.request.handlers.file;

import http.request.builder.RequestHeaderBuilder;
import http.request.ContentType;
import http.response.Response;
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

public class GetFileContentResponseHandlerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseFolder;

    @Before
    public void setUp() throws Exception {
        baseFolder = temporaryFolder.newFolder();
    }

    @Test
    public void containsPlainContentType() throws IOException {
        FileUtils.writeStringToFile(new File(baseFolder, "plainFile"), "Hello world");
        Response response = new GetFileContentRequestHandler().getResponse(baseFolder, getBuild("/plainFile"));
        assertThat(response.getContentType()).isEqualTo(ContentType.PLAIN);
    }

    private Request getBuild(String path) {
        return new RequestBuilder().withHeader(new RequestHeaderBuilder().withURI(path).build()).build();
    }

    @Test
    public void containsJPEGContentType() throws IOException {
        FileUtils.writeStringToFile(new File(baseFolder, "image.jpeg"), "imageData");
        Response response = new GetFileContentRequestHandler().getResponse(baseFolder, getBuild("/image.jpeg"));
        assertThat(response.getContentType()).isEqualTo(ContentType.JPEG);
    }

    @Test
    public void containsContentLength() throws IOException {
        File requestedFile = new File(baseFolder, "plainFile");
        FileUtils.writeStringToFile(requestedFile, "Hello world");
        Response response = new GetFileContentRequestHandler().getResponse(baseFolder, getBuild("/plainFile"));
        assertThat(response.getContentLength()).isEqualTo(requestedFile.length());
    }
}