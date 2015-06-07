package http.response;

import builders.RequestHeaderBuilder;
import http.Response;
import http.request.builder.RequestBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetDirectoryResponseHandlerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File folder;
    private GetDirectoryResponseHandler getDirectoryResponseResolver;

    @Before
    public void setUp() throws IOException {
        folder = temporaryFolder.newFolder();
        getDirectoryResponseResolver = new GetDirectoryResponseHandler();
    }

    @Test
    public void directoryContents() throws IOException {
        createFile(folder, "file1");
        createFile(folder, "file2");
        Response response = getDirectoryResponseResolver.getResponse(folder, new RequestBuilder().withHeader(new RequestHeaderBuilder().withPath("/").build()).build());
        assertThat(response.getContentsAsString()).contains("file1", "file2");
    }

    @Test
    public void directoryLinks() throws IOException {
        String filename = "file1";
        createFile(folder, filename);
        Response response = getDirectoryResponseResolver.getResponse(folder, new RequestBuilder().withHeader(new RequestHeaderBuilder().withPath("/").build()).build());
        assertThat(response.getContentsAsString()).contains(String.format("<a href=\"/%s\">%s</a>", filename, filename));
    }

    private void createFile(File folder, String name) throws IOException {
        File file = new File(folder, name);
        assertThat(file.createNewFile());
    }
}