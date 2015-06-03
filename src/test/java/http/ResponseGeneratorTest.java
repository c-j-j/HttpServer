package http;

import builders.RequestBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

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
        String fileContent = "Hello, World";
        writeToFile("tempFile", fileContent);
        Response response = new ResponseGenerator(s -> new RequestBuilder()
                .withPath("/tempFile")
                .build(), baseFolder).apply(new FakeSocket());
        assertThat(response.getContents()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void yields404ResponseWhenFileDoesNotExist() {
        Request request = new RequestBuilder().withPath("/nonExistentFile").build();
        Response response = new ResponseGenerator(s -> request, baseFolder).apply(new FakeSocket());
        assertThat(response.getContents()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.NOT_FOUND);
    }

    private void writeToFile(String fileName, String fileContents) throws IOException {
        File requestedFile = new File(baseFolder, fileName);
        FileUtils.writeStringToFile(requestedFile, fileContents);
    }
}