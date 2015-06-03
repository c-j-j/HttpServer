package http;

import builders.ResponseBuilder;

import java.io.File;
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
            return request.getResponse(baseFolder, request);
        } catch (RuntimeException e) {
            return new ResponseBuilder().build();
        }
    }

}
