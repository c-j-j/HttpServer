package http;

import builders.ResponseBuilder;
import com.google.common.io.CharSource;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSerializerTest {

    private ResponseSerializer responseSerializer;

    @Before
    public void setUp() throws Exception {
        responseSerializer = new ResponseSerializer();
    }

    @Test
    public void containsHeaderLineWithStatusCode(){
        Response response = new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
        String payload = responseSerializer.toPayload(response);
        HTTPStatusCode statusCode = response.getStatusCode();
        assertThat(payload).contains(String.format("HTTP/1.1 %d %s", statusCode.getCode(), statusCode.getStatus()));
    }

    @Test
    public void containsContent(){
        Response response = new ResponseBuilder().withContent("Hello World").build();
        assertThat(responseSerializer.toPayload(response)).contains(String.format("\n\n%s", "Hello World"));
    }

    @Test
    public void containsLocation(){
        String location = "SomeLocation";
        Response response = new ResponseBuilder().withLocation(location).build();
        assertThat(responseSerializer.toPayload(response)).contains(String.format("Location: %s", location));
    }

    @Test
    public void charsource() throws IOException {
        CharSource charSource = CharSource.concat(CharSource.wrap("hello"), CharSource.wrap("Hello World\nGoodbye World"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(charSource.openBufferedStream(), byteArrayOutputStream);
        byteArrayOutputStream.close();
        System.out.println(byteArrayOutputStream.toString());
    }
}
