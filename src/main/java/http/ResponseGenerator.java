package http;

import builders.ResponseBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Function;

public class ResponseGenerator implements Function<Socket, Response> {

    private final Function<Socket, Request> requestParser;
    private final File baseFolder;

    public ResponseGenerator(Function<Socket, Request> requestParser, File baseFolder) {
        this.requestParser = requestParser;
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            return new ResponseBuilder()
                    .withStatusCode(HTTPStatusCode.OK)
                    .withContent(readFile(new File(baseFolder, request.getPath())))
                    .build();
        } catch (RuntimeException e) {
            return new ResponseBuilder().build();
        }
    }

    private String readFile(File requestedFile) {
        String responseContents;
        try {
            responseContents = getResponseFromFile(requestedFile);
        } catch (IOException e) {
            responseContents = "";
        }
        return responseContents;
    }

    private String getResponseFromFile(File requestedFile) throws IOException {
        return FileUtils.readFileToString(requestedFile);
    }
}
