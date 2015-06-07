package http;

import builders.ResponseBuilder;

import java.io.File;
import java.util.function.Function;

public class ResponseGenerator implements Function<RequestHeader, Response> {

    private final ResourceRepository resourceRepository;
    private final File baseFolder;

    public ResponseGenerator(ResourceRepository resourceRepository, File baseFolder) {
        this.resourceRepository = resourceRepository;
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(RequestHeader requestHeader) {
        try {
            return generateResponse(requestHeader);
        } catch (RuntimeException e) {
            return new ResponseBuilder().build();
        }
    }

    private Response generateResponse(RequestHeader requestHeader) {
        if (resourceRepository.canRespond(requestHeader)) {
            return resourceRepository.getResponse(requestHeader);
        } else {
            return requestHeader.getResponse(baseFolder, requestHeader);
        }
    }

}
