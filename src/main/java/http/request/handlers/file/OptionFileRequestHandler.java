package http.request.handlers.file;

import http.response.builders.ResponseBuilder;
import http.request.HTTPAction;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

import java.io.File;

public class OptionFileRequestHandler implements FileRequestHandler {

    @Override
    public Response getResponse(File baseFolder, Request requestHeader) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withAllowedOptions(HTTPAction.GET, HTTPAction.HEAD, HTTPAction.POST, HTTPAction.OPTIONS, HTTPAction.PUT)
                .build();
    }
}
