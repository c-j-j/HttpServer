package http.response;

import builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;

import java.io.File;

public class GetDirectoryResponseResolver implements ResponseResolver{
    @Override
    public Response getResponse(File baseFolder, RequestHeader requestHeader) {
        File requestedDirectory = new File(baseFolder, requestHeader.getPath());
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withContent(getDirectories(requestedDirectory))
                .build();
    }

    private String getDirectories(File directory) {
        File[] files = directory.listFiles();
        StringBuilder directoryBuilder = new StringBuilder();
        for (File file : files) {
            String fileName = file.getName();
            directoryBuilder.append(String.format("<a href=\"/%s\">%s</a>", fileName, fileName)).append("\n");
        }
        return directoryBuilder.toString();
    }
}
