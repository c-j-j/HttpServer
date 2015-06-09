package http.request.handlers;

import http.request.builder.RequestHeaderBuilder;
import http.response.builders.ResponseBuilder;
import com.google.common.io.ByteSource;
import http.request.ByteRange;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;
import http.request.builder.RequestBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PartialContentRequestWrapperTest {

    @Test
    public void callsWrappedResolverWhenNoRangeGiven() {
        Response wrappedResponse = new ResponseBuilder().build();
        Response response = new PartialContentRequestWrapper(r -> wrappedResponse).apply(buildStandardRequest());
        assertThat(response).isEqualTo(wrappedResponse);
    }

    @Test
    public void returnsPartialContent() throws IOException {
        ByteSource wrappedResponseBody = buildByteSource("HelloWorld");
        ByteRange byteRange = new ByteRange(0, 3);
        Response response = new PartialContentRequestWrapper(r -> buildResponse(wrappedResponseBody)).apply(buildRequest(byteRange));
        assertThat(response.getBody().read()).isEqualTo(extractPartOfBody(wrappedResponseBody, byteRange, getContentSize(wrappedResponseBody)).read());
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.PARTIAL_CONTENT);
    }

    private Request buildStandardRequest() {
        return new RequestBuilder().withHeader(new RequestHeaderBuilder().build()).build();
    }

    private int getContentSize(ByteSource wrappedResponseBody) throws IOException {
        return (int) wrappedResponseBody.size();
    }

    private ByteSource extractPartOfBody(ByteSource wrappedResponseBody, ByteRange byteRange, int contentSize) {
        return wrappedResponseBody.slice(byteRange.offset(contentSize), byteRange.length(contentSize));
    }

    private ByteSource buildByteSource(String responseBody) {
        return ByteSource.wrap(responseBody.getBytes());
    }

    private Response buildResponse(ByteSource byteSource) {
        return new ResponseBuilder().withBody(byteSource).build();
    }

    private Request buildRequest(ByteRange byteRange) {
        return new RequestBuilder().withHeader(new RequestHeaderBuilder().withRange(Optional.of(byteRange)).build()).build();
    }
}