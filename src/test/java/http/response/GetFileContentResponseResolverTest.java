package http.response;

import builders.RequestBuilder;
import http.ContentType;
import http.Response;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetFileContentResponseResolverTest {

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
        Response response = new GetFileContentResponseResolver().getResponse(baseFolder, new RequestBuilder().withPath("/plainFile").build());
        assertThat(response.getContentType()).isEqualTo(ContentType.PLAIN);
    }

    @Test
    public void containsJPEGContentType() throws IOException {
        FileUtils.writeStringToFile(new File(baseFolder, "image.jpeg"), "imageData");
        Response response = new GetFileContentResponseResolver().getResponse(baseFolder, new RequestBuilder().withPath("/image.jpeg").build());
        assertThat(response.getContentType()).isEqualTo(ContentType.JPEG);
    }
}