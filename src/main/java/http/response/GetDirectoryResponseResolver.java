package http.response;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

import java.io.File;

public class GetDirectoryResponseResolver implements ResponseResolver{
    @Override
    public Response getResponse(File baseFolder, Request request) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withContent("directory")
                .build();
    }
}
