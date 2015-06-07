package http.response;

import http.RequestHeader;
import http.Response;

import java.io.File;

public class GETResponseResolver implements ResponseResolver {

    private final NotFoundResponseResolver notFoundResponseResolver = new NotFoundResponseResolver();
    private final GetDirectoryResponseResolver getDirectoryResponseResolver = new GetDirectoryResponseResolver();
    private final GetFileContentResponseResolver getFileContentResponseResolver = new GetFileContentResponseResolver();

    @Override
    public Response getResponse(File baseFolder, RequestHeader requestHeader) {
        File requestedFile = getRequestedFile(baseFolder, requestHeader);

        if (!requestedFile.exists()) {
            return notFoundResponseResolver.getResponse(baseFolder, requestHeader);
        } else if (requestedFile.isDirectory()) {
            return getDirectoryResponseResolver.getResponse(baseFolder, requestHeader);
        } else {
            return getFileContentResponseResolver.getResponse(baseFolder, requestHeader);
        }
    }

    private File getRequestedFile(File baseFolder, RequestHeader requestHeader) {
        return new File(baseFolder, requestHeader.getPath());
    }
}
