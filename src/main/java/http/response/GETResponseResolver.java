package http.response;

import http.Response;
import http.request.Request;

import java.io.File;

public class GETResponseResolver implements ResponseResolver {

    private final NotFoundResponseResolver notFoundResponseResolver = new NotFoundResponseResolver();
    private final GetDirectoryResponseResolver getDirectoryResponseResolver = new GetDirectoryResponseResolver();
    private final GetFileContentResponseResolver getFileContentResponseResolver = new GetFileContentResponseResolver();

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
