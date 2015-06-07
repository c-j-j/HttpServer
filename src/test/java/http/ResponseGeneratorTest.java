package http;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseGeneratorTest {

    private static final String FILE_NAME = "tempFile";
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File baseFolder;
    private ResponseGenerator responseGenerator;
    private StubResourceRepository resourceRepository;
    private RequestHeader requestHeader;

    @Before
    public void setUp() throws Exception {
        resourceRepository = new StubResourceRepository();
        baseFolder = temporaryFolder.newFolder();
        requestHeader = new RequestHeaderBuilder().withPath("/tempFile").build();
        responseGenerator = new ResponseGenerator(resourceRepository, baseFolder);
    }

    @Test
    public void usesFileSystemWhenResourcesNotAvailable() throws IOException {
        String fileContent = "Hello, World";
        writeToFile(FILE_NAME, fileContent);
        Response response = responseGenerator.apply(requestHeader);
        assertThat(response.getContentsAsString()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void usesResourcesWhenAvailable() {
        Response response = new ResponseBuilder().build();
        resourceRepository.stubResponse(requestHeader, response);
        assertThat(responseGenerator.apply(requestHeader)).isEqualTo(response);
    }

    private void writeToFile(String fileName, String fileContents) throws IOException {
        File requestedFile = new File(baseFolder, fileName);
        FileUtils.writeStringToFile(requestedFile, fileContents);
    }

    private class StubResourceRepository extends ResourceRepository {

        private RequestHeader requestHeader;
        private Response response;

        public StubResourceRepository() {
            super(Collections.emptySet());
        }

        public void stubResponse(RequestHeader requestHeader, Response response) {
            this.requestHeader = requestHeader;
            this.response = response;
        }

        @Override
        public boolean canRespond(RequestHeader requestHeader) {
            return this.requestHeader == requestHeader;
        }

        @Override
        public Response getResponse(RequestHeader requestHeader) {
            return response;
        }
    }
}