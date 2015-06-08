package http;

import http.fakes.FakeSocket;
import http.response.Response;
import http.response.Serializer;
import http.response.builders.ResponseBuilder;
import com.google.common.io.ByteSource;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SocketWriterTest {

    private ByteArrayOutputStream outputStreamWritten;
    private FakeSocket socket;

    @Before
    public void setUp() throws Exception {
        outputStreamWritten = new ByteArrayOutputStream();
        socket = new FakeSocket(outputStreamWritten);
    }

    @Test
    public void WritesResponseToOutput() {
        Response response = new ResponseBuilder().build();
        Serializer serializer = response1 -> ByteSource.wrap("response text".getBytes());
        new SocketWriter(serializer).accept(socket, response);
        assertThat(outputStreamWritten.toString()).isEqualTo("response text");

    }
}