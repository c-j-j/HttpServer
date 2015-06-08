package http.request.handlers.file;

import http.response.builders.ResponseBuilder;
import http.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

import java.io.File;

public class GetDirectoryRequestHandler implements FileRequestHandler {
    @Override
    public Response getResponse(File baseFolder, Request request) {
        File requestedDirectory = new File(baseFolder, request.getPath());
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
