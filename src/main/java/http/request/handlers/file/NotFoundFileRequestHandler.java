package http.request.handlers.file;

import http.response.builders.ResponseBuilder;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

import java.io.File;

public class NotFoundFileRequestHandler implements FileRequestHandler {
    @Override
    public Response getResponse(File baseFolder, Request request) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.NOT_FOUND)
                .build();

    }
}
