package http;

import builders.ResponseBuilder;

import java.io.File;
import java.net.Socket;
import java.util.function.Function;

public class ResponseGenerator implements Function<Socket, Response> {

    private final ResourceRepository resourceRepository;
    private final Function<Socket, Request> requestParser;
    private final File baseFolder;

    public ResponseGenerator(ResourceRepository resourceRepository, Function<Socket, Request> requestParser, File baseFolder) {
        this.resourceRepository = resourceRepository;
        this.requestParser = requestParser;
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            return generateResponse(request);
        } catch (RuntimeException e) {
            return new ResponseBuilder().build();
        }
    }

    private Response generateResponse(Request request) {
        if (resourceRepository.canRespond(request)){
            return resourceRepository.getResponse(request);
        }else{
            return request.getResponse(baseFolder, request);
        }
    }

}
