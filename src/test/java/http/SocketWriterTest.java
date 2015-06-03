package http;

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
        Response response = new Response();
        new SocketWriter().accept(socket, response);
       assertThat(outputStreamWritten.toString()).contains("HTTP/1.1 200 OK\n\nHello");

    }
}