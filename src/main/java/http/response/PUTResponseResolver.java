package http.response;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

import java.io.File;

public class PUTResponseResolver implements ResponseResolver {
    @Override
    public Response getResponse(File baseFolder, Request request) {
        if (new File(baseFolder, request.getPath()).exists()){
            return new ResponseBuilder().withStatusCode(HTTPStatusCode.METHOD_NOT_ALLOWED).build();
        }else {
            return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
        }
    }
}
