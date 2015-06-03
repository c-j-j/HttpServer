package http;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

public class ResponseGenerator implements Function<Request, Response> {

    private final File baseFolder;

    public ResponseGenerator(File baseFolder) {
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(Request request) {
        File requestedFile = new File(baseFolder, request.getPath());
        String responseContents = null;
        try {
            responseContents = getResponseFromFile(requestedFile);
        } catch (IOException e) {
            return new Response("");
        }
        return new Response(responseContents);
    }

    private String getResponseFromFile(File requestedFile) throws IOException {
        return FileUtils.readFileToString(requestedFile);
    }
}
