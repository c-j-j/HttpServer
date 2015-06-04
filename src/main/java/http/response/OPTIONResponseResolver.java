package http.response;

import builders.ResponseBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.Request;
import http.Response;

import java.io.File;

public class OPTIONResponseResolver implements ResponseResolver{

    @Override
    public Response getResponse(File baseFolder, Request request) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withAllowedOptions(HTTPAction.GET, HTTPAction.HEAD, HTTPAction.POST, HTTPAction.OPTIONS, HTTPAction.PUT)
                .build();
    }
}
