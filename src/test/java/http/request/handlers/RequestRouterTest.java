package http.request.handlers;

import http.response.HTTPStatusCode;
import http.resource.ResourceRepository;
import http.request.Request;
import http.request.builder.RequestBuilder;
import http.request.builder.RequestHeaderBuilder;
import http.response.Response;
import http.response.builders.ResponseBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestRouterTest {

    private static final String FILE_NAME = "tempFile";
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File baseFolder;
    private RequestRouter requestRouter;
    private StubResourceRepository resourceRepository;
    private Request request;

    @Before
    public void setUp() throws Exception {
        resourceRepository = new StubResourceRepository();
        baseFolder = temporaryFolder.newFolder();
        request = new RequestBuilder().withHeader(new RequestHeaderBuilder().withURI("/tempFile").build()).build();
        requestRouter = new RequestRouter(resourceRepository, baseFolder);
    }

    @Test
    public void usesFileSystemWhenResourcesNotAvailable() throws IOException {
        String fileContent = "Hello, World";
        writeToFile(FILE_NAME, fileContent);
        Response response = requestRouter.apply(request);
        assertThat(response.getBodyAsString()).isEqualTo(fileContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void usesResourcesWhenAvailable() {
        Response response = new ResponseBuilder().build();
        resourceRepository.stubResponse(request, response);
        assertThat(requestRouter.apply(request)).isEqualTo(response);
    }

    private void writeToFile(String fileName, String fileContents) throws IOException {
        File requestedFile = new File(baseFolder, fileName);
        FileUtils.writeStringToFile(requestedFile, fileContents);
    }

    private class StubResourceRepository extends ResourceRepository {

        private Request request;
        private Response response;

        public StubResourceRepository() {
            super(Collections.emptySet());
        }

        public void stubResponse(Request request, Response response) {
            this.request= request;
            this.response = response;
        }

        @Override
        public boolean canRespond(Request request) {
            return this.request == request;
        }

        @Override
        public Response getResponse(Request request) {
            return response;
        }
    }
}