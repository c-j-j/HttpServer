package http;

import builders.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSerializerTest {

    private ResponseSerializer responseSerializer;

    @Before
    public void setUp() throws Exception {
        responseSerializer = new ResponseSerializer();
    }

    @Test
    public void responseContainsHeaderLine(){
        Response response = new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
        String payload = responseSerializer.toPayload(response);
        HTTPStatusCode statusCode = response.getStatusCode();
        assertThat(payload).contains(String.format("HTTP/1.1 %d %s", statusCode.getCode(), statusCode.getStatus()));
    }

    @Test
    public void responseContainsContent(){
        Response response = new ResponseBuilder().withContent("Hello World").build();
        String payload = responseSerializer.toPayload(response);
        assertThat(payload).contains(String.format("\n\n%s", "Hello World"));
    }
}