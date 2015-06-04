package http.response;

import builders.ResponseBuilder;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

import java.io.File;
import java.nio.charset.Charset;

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

    private CharSource readFile(File requestedFile) {
        return Files.asCharSource(requestedFile, Charset.defaultCharset());
    }
}
