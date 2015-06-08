package http.request.handlers;

import http.response.builders.ResponseBuilder;
import com.google.common.io.ByteSource;
import http.request.ByteRange;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Function;

public class PartialContentRequestWrapper implements Function<Request, Response> {

    private final Function<Request, Response> requestResolver;

    public PartialContentRequestWrapper(Function<Request, Response> requestResolver) {
        this.requestResolver = requestResolver;
    }

    @Override
    public Response apply(Request request) {
        Response wrappedResponse = requestResolver.apply(request);

        if (getRange(request).isPresent()) {
            return new ResponseBuilder()
                    .withStatusCode(HTTPStatusCode.PARTIAL_CONTENT)
                    .withBody(extractPartOfBody(wrappedResponse, getSizeOfResponseBody(wrappedResponse), getRange(request).get()))
                    .build();
        } else {
            return wrappedResponse;
        }
    }

    private int getSizeOfResponseBody(Response wrappedResponse) {
        try {
            return (int) wrappedResponse.getBody().size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ByteSource extractPartOfBody(Response wrappedResponse, int contentSize, ByteRange byteRange) {
        return wrappedResponse.getBody().slice(byteRange.offset(contentSize), byteRange.length(contentSize));
    }

    private Optional<ByteRange> getRange(Request request) {
        return request.getHeader().getRange();
    }
}
