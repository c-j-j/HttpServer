package http;

import builders.RequestBuilder;
import http.fakes.TestFunction;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseGeneratorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseFolder;

    @Before
    public void setUp() throws Exception {
        baseFolder = temporaryFolder.newFolder();
    }

    @Test
    public void fileContents() throws IOException {
        File requestedFile = new File(baseFolder, "tempFile");
        String fileContent = "Hello, World";
        FileUtils.writeStringToFile(requestedFile, fileContent);
        Request request = new RequestBuilder().withPath("/tempFile").build();
        Response response = new ResponseGenerator(s -> request, baseFolder).apply(new FakeSocket());
        assertThat(response.getContents()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

}