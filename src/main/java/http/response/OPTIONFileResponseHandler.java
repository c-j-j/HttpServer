package http.response;

import builders.ResponseBuilder;
import http.HTTPAction;
import http.HTTPStatusCode;
import http.Response;
import http.request.Request;

import java.io.File;

public class OptionFileResponseHandler implements FileResponseHandler {

    @Override
    public Response getResponse(File baseFolder, Request requestHeader) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withAllowedOptions(HTTPAction.GET, HTTPAction.HEAD, HTTPAction.POST, HTTPAction.OPTIONS, HTTPAction.PUT)
                .build();
    }
}
