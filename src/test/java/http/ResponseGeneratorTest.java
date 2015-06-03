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
        File requestedFile = new File(baseFolder, "tempFile");
        FileUtils.writeStringToFile(requestedFile, "Hello, World");
        Response response = new ResponseGenerator(baseFolder).apply(new RequestBuilder().withPath("/tempFile").build());
        assertThat(response.getContents()).isEqualTo("Hello, World");
    }

}