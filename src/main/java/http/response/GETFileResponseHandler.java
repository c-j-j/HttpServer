package http.response;

import http.Response;
import http.request.Request;

import java.io.File;

public class GetFileResponseHandler implements FileResponseHandler {

    private final NotFoundFileResponseHandler notFoundResponseResolver = new NotFoundFileResponseHandler();
    private final GetDirectoryResponseHandler getDirectoryResponseResolver = new GetDirectoryResponseHandler();
    private final GetFileContentResponseHandler getFileContentResponseResolver = new GetFileContentResponseHandler();

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
