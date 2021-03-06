package http.integration;

import http.request.HTTPAction;
import http.builders.HTTPRequestMessageBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class GetHTTPTest extends IntegrationTestBase {

    @Test
    public void getFileContent() throws IOException {
        createFile("some_file", "Hello World!");
        Socket socket = new Socket("localhost", PORT);
        sendHTTPRequest(socket, new HTTPRequestMessageBuilder()
                .withAction(HTTPAction.GET)
                .withPath("/some_file")
                .build());
        assertThat(readHTTPResponse(socket)).contains("Hello World!");
        socket.close();
    }
    @Test
    public void getsDirectoryContents() throws IOException {
        createFile("some_file", "Hello World!");
        Socket socket = new Socket("localhost", PORT);
        sendHTTPRequest(socket, new HTTPRequestMessageBuilder()
                .withAction(HTTPAction.GET)
                .withPath("/")
                .build());
        assertThat(readHTTPResponse(socket)).contains("some_file");
        socket.close();
    }
}
