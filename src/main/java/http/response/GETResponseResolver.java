package http.response;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.Request;
import http.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class GETResponseResolver implements ResponseResolver {

    @Override
    public Response getResponse(File baseFolder, Request request) {
        if (!getRequestedFile(baseFolder, request).exists()) {
            return new ResponseBuilder()
                    .withStatusCode(HTTPStatusCode.NOT_FOUND)
                    .build();
        } else {
            return new ResponseBuilder()
                    .withStatusCode(HTTPStatusCode.OK)
                    .withContent(readFile(getRequestedFile(baseFolder, request)))
                    .build();
        }
    }

    private File getRequestedFile(File baseFolder, Request request) {
        return new File(baseFolder, request.getPath());
    }

    private String readFile(File requestedFile) {
        String responseContents;
        try {
            responseContents = getResponseFromFile(requestedFile);
        } catch (IOException e) {
            responseContents = "";
        }
        return responseContents;
    }

    private String getResponseFromFile(File requestedFile) throws IOException {
        return FileUtils.readFileToString(requestedFile);
    }
}
