package http.response;

import http.Request;
import http.Response;

import java.io.File;

public interface ResponseResolver {
    Response getResponse(File baseFolder, Request request);
}
