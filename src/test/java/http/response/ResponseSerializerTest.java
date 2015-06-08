package http.response;

import http.HTTPStatusCode;
import http.request.ContentType;
import http.request.HTTPAction;
import http.response.builders.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSerializerTest {

    private ResponseSerializer responseSerializer;

    @Before
    public void setUp() throws Exception {
        responseSerializer = new ResponseSerializer();
    }

    @Test
    public void containsHeaderLineWithStatusCode() throws IOException {
        Response response = new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
        String payload = serializeResponse(response);
        HTTPStatusCode statusCode = response.getStatusCode();
        assertThat(payload).contains(String.format("HTTP/1.1 %d %s", statusCode.getCode(), statusCode.getStatus()));
    }

    @Test
    public void containsContent() throws IOException {
        Response response = new ResponseBuilder().withContent("Hello World").build();
        assertThat(serializeResponse(response)).contains(String.format("\n\n%s", "Hello World"));
    }

    @Test
    public void containsLocation() throws IOException {
        String location = "SomeLocation";
        Response response = new ResponseBuilder().withLocation(location).build();
        assertThat(serializeResponse(response)).contains(String.format("Location: %s", response.getLocation()));
    }

    @Test
    public void containsContentType() throws IOException {
        Response response = new ResponseBuilder().withContentType(ContentType.PLAIN).build();
        assertThat(serializeResponse(response)).contains(String.format("Content-Type: %s", response.getContentType().getDescription()));
    }

    @Test
    public void containsAllowedOptions() throws IOException {
        Response response = new ResponseBuilder().withAllowedOptions(HTTPAction.GET, HTTPAction.OPTIONS).build();
        assertThat(serializeResponse(response)).contains(String.format("Allow: %s,%s", HTTPAction.GET, HTTPAction.OPTIONS));
    }

    private String serializeResponse(Response response) throws IOException {
        return new String(responseSerializer.toPayload(response).read());
    }
}
