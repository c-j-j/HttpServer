package http;

import builders.ResponseBuilder;
import http.request.Request;

import java.io.File;
import java.util.function.Function;

public class ResponseGenerator implements Function<Request, Response> {

    private final ResourceRepository resourceRepository;
    private final File baseFolder;

    public ResponseGenerator(ResourceRepository resourceRepository, File baseFolder) {
        this.resourceRepository = resourceRepository;
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(Request request) {
        try {
            return generateResponse(request);
        } catch (RuntimeException e) {
            return new ResponseBuilder().build();
        }
    }

    private Response generateResponse(Request request) {
        if (resourceRepository.canRespond(request)) {
            return resourceRepository.getResponse(request);
        } else {
            return request.getHeader().getResponse(baseFolder, request);
        }
    }

}
