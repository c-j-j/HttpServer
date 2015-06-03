package http.integration;

import http.HTTPAction;
import http.builders.HTTPRequestBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class GetHTTPTest extends IntegrationTestBase {

    @Test
    public void getsContentFromRequestedFile() throws IOException {
        createFile("some_file", "Hello World!");
        Socket socket = new Socket("localhost", PORT);
        System.out.println("SOCKET => " + socket.getPort());
        sendHTTPRequest(socket, new HTTPRequestBuilder()
                .withAction(HTTPAction.GET)
                .withPath("/some_file")
                .build());
        assertThat(readHTTPResponse(socket)).contains("Hello World!");
        socket.close();
    }
}
