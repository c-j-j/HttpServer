package http.request.handlers.file;

import http.response.Response;
import http.request.Request;

import java.io.File;

public interface FileRequestHandler {
    Response getResponse(File baseFolder, Request request);
}
