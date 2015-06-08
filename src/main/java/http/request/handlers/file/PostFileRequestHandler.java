package http.request.handlers.file;

import http.response.builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

import java.io.File;

public class PostFileRequestHandler implements FileRequestHandler {
    @Override
    public Response getResponse(File baseFolder, Request request) {
        if (new File(baseFolder, request.getPath()).exists()){
            return new ResponseBuilder().withStatusCode(HTTPStatusCode.METHOD_NOT_ALLOWED).build();
        }else {
            return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
        }

    }
}
