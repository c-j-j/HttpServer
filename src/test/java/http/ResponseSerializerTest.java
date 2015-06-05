package http;

import builders.ResponseBuilder;
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
        String payload = responseToString(response);
        HTTPStatusCode statusCode = response.getStatusCode();
        assertThat(payload).contains(String.format("HTTP/1.1 %d %s", statusCode.getCode(), statusCode.getStatus()));
    }

    @Test
    public void containsContent() throws IOException {
        Response response = new ResponseBuilder().withContent("Hello World").build();
        assertThat(responseToString(response)).contains(String.format("\n\n%s", "Hello World"));
    }

    private String responseToString(Response response) throws IOException {
        return new String(responseSerializer.toPayload(response).read());
    }

    @Test
    public void containsLocation() throws IOException {
        String location = "SomeLocation";
        Response response = new ResponseBuilder().withLocation(location).build();
        assertThat(responseToString(response)).contains(String.format("Location: %s", location));
    }

    @Test
    public void containsContentType() throws IOException {
        Response response = new ResponseBuilder().withContentType(ContentType.PLAIN).build();
        assertThat(responseToString(response)).contains(String.format("Content-Type: %s", ContentType.PLAIN.getDescription()));
    }

    @Test
    public void containsAllowedOptions() throws IOException {
        Response response = new ResponseBuilder().withAllowedOptions(HTTPAction.GET, HTTPAction.OPTIONS).build();
        assertThat(responseToString(response)).contains(String.format("Allow: %s,%s", HTTPAction.GET, HTTPAction.OPTIONS));
    }
}
