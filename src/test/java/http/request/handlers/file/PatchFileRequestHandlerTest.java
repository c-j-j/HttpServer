package http.request.handlers.file;

import http.request.builder.RequestHeaderBuilder;
import http.response.HTTPStatusCode;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PatchFileRequestHandlerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseDir;
    private File fileToPatch;

    @Before
    public void setUp() throws IOException {
        baseDir = temporaryFolder.newFolder();
        fileToPatch = createFile("patched_file");
    }

    @Test
    public void patchesFileWhenIfMatchValueNotProvided() throws IOException {
        String patchedContent = "patchedContent";
        Response response = new PatchFileRequestHandler()
                .getResponse(baseDir, buildRequest(fileToPatch.getName(), patchedContent, Optional.empty()));
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.NO_CONTENT);
        assertThat(FileUtils.readFileToString(fileToPatch)).isEqualTo(patchedContent);
    }

    @Test
    public void patchesFileWhenIfMatchValueMatches() throws IOException {
        FileUtils.writeStringToFile(fileToPatch, "originalContent");
        String patchedContent = "patchedContent";
        Response response = new PatchFileRequestHandler()
                .getResponse(baseDir, buildRequest(fileToPatch.getName(), patchedContent, Optional.of(PatchFileRequestHandler.Sha1Hash.generateHash(fileToPatch))));
        assertThat(FileUtils.readFileToString(fileToPatch)).isEqualTo(patchedContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.NO_CONTENT);
    }

    @Test
    public void returnsPreconditionFailedResponseWhenETagFailsToMatch() throws IOException {
        String originalContent = "originalContent";
        FileUtils.writeStringToFile(fileToPatch, originalContent);
        Response response = new PatchFileRequestHandler()
                .getResponse(baseDir, buildRequest(fileToPatch.getName(), "patchedContent", Optional.of("InvalidHash")));
        assertThat(FileUtils.readFileToString(fileToPatch)).isEqualTo(originalContent);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.PRECONDITION_FAILED);
    }

    private File createFile(String patchedFile) throws IOException {
        File fileToPatch = new File(baseDir, patchedFile);
        assertThat(fileToPatch.createNewFile());
        return fileToPatch;
    }

    private Request buildRequest(final String patchedFilename, String patchedContent, Optional<String> hashForOriginalContent) {
        return new RequestBuilder()
                .withHeader(new RequestHeaderBuilder()
                        .withIfMatchValue(hashForOriginalContent)
                        .withURI("/" + patchedFilename)
                        .build())
                .withBody(patchedContent).build();
    }
}