package http.response;

import http.RequestHeader;
import http.Response;

import java.io.File;

public interface ResponseResolver {
    Response getResponse(File baseFolder, RequestHeader requestHeader);
}
