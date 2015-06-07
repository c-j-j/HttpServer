package http.response;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;

import java.io.File;

public class NotFoundResponseResolver implements ResponseResolver {
    @Override
    public Response getResponse(File baseFolder, RequestHeader requestHeader) {
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.NOT_FOUND)
                .build();

    }
}
