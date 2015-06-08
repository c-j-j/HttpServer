package http.request.handlers.file;

import http.response.Response;
import http.request.Request;

import java.io.File;

public class GetFileRequestHandler implements FileRequestHandler {

    private final NotFoundFileRequestHandler notFoundResponseResolver = new NotFoundFileRequestHandler();
    private final GetDirectoryRequestHandler getDirectoryResponseResolver = new GetDirectoryRequestHandler();
    private final GetFileContentRequestHandler getFileContentResponseResolver = new GetFileContentRequestHandler();

    @Override
    public Response getResponse(File baseFolder, Request request) {
        File requestedFile = getRequestedFile(baseFolder, request);

        if (!requestedFile.exists()) {
            return notFoundResponseResolver.getResponse(baseFolder, request);
        } else if (requestedFile.isDirectory()) {
            return getDirectoryResponseResolver.getResponse(baseFolder, request);
        } else {
            return getFileContentResponseResolver.getResponse(baseFolder, request);
        }
    }

    private File getRequestedFile(File baseFolder, Request request) {
        return new File(baseFolder, request.getPath());
    }
}
