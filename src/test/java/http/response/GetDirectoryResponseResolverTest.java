package http.response;

import builders.RequestBuilder;
import http.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetDirectoryResponseResolverTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File folder;
    private GetDirectoryResponseResolver getDirectoryResponseResolver;

    @Before
    public void setUp() throws IOException {
        folder = temporaryFolder.newFolder();
        getDirectoryResponseResolver = new GetDirectoryResponseResolver();
    }

    @Test
    public void directoryContents() throws IOException {
        createFile(folder, "file1");
        createFile(folder, "file2");
        Response response = getDirectoryResponseResolver.getResponse(folder, new RequestBuilder().withPath("/").build());
        assertThat(response.getContentsAsString()).contains("file1", "file2");
    }

    @Test
    public void directoryLinks() throws IOException {
        String filename = "file1";
        createFile(folder, filename);
        Response response = getDirectoryResponseResolver.getResponse(folder, new RequestBuilder().withPath("/").build());
        assertThat(response.getContentsAsString()).contains(String.format("<a href=\"/%s\">%s</a>", filename, filename));
    }

    private void createFile(File folder, String name) throws IOException {
        File file = new File(folder, name);
        assertThat(file.createNewFile());
    }
}