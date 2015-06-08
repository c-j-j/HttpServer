package http.request;

import builders.ResponseBuilder;
import com.google.common.io.ByteSource;
import http.ByteRange;
import http.HTTPStatusCode;
import http.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Function;

public class PartialContentRequestResolver implements Function<Request, Response> {

    private final Function<Request, Response> requestResolver;

    public PartialContentRequestResolver(Function<Request, Response> requestResolver) {
        this.requestResolver = requestResolver;
    }

    @Override
    public Response apply(Request request) {
        Response wrappedResponse = requestResolver.apply(request);

        if (getRange(request).isPresent()) {
            return new ResponseBuilder()
                    .withStatusCode(HTTPStatusCode.PARTIAL_CONTENT)
                    .withContent(extractPartOfBody(wrappedResponse, getSizeOfResponseBody(wrappedResponse), getRange(request).get()))
                    .build();
        } else {
            return wrappedResponse;
        }
    }

    private int getSizeOfResponseBody(Response wrappedResponse) {
        try {
            return (int) wrappedResponse.getContents().size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ByteSource extractPartOfBody(Response wrappedResponse, int contentSize, ByteRange byteRange) {
        return wrappedResponse.getContents().slice(byteRange.offset(contentSize), byteRange.length(contentSize));
    }

    private Optional<ByteRange> getRange(Request request) {
        return request.getHeader().getRange();
    }
}