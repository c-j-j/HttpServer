package http.response;

import http.Response;
import http.request.Request;

import java.io.File;

public interface FileResponseHandler {
    Response getResponse(File baseFolder, Request request);
}
