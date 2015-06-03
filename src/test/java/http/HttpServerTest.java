package http;

import http.builders.HTTPRequestBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

    private static final int PORT = 5001;
    private ExecutorService threadPool;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseDirectory;

    @Before
    public void setUp() throws IOException {
        baseDirectory = temporaryFolder.newFolder();
        threadPool = Executors.newFixedThreadPool(2);
    }

    @After
    public void tearDown() {
        threadPool.shutdown();
    }

    @Test
    public void getsContentFromRequestedFile() throws IOException {
        startHTTPServer();
        Socket socket = new Socket("localhost", PORT);
        createFile("some_file", "Hello World!");
        writeToSocket(socket, new HTTPRequestBuilder()
                .withAction(HTTPAction.GET)
                .withPath("/some_file")
                .build());
        assertThat(readHTTPResponse(socket)).contains("Hello World!");
    }

    private void startHTTPServer() {
        threadPool.submit(() -> new HttpServer(baseDirectory, PORT).start());
    }

    private String readHTTPResponse(Socket socket) throws IOException {
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(socket.getInputStream(), stringWriter);
        return stringWriter.toString();
    }

    private void createFile(String filename, String content) throws IOException {
        File requestedResource = new File(baseDirectory, filename);
        FileUtils.writeStringToFile(requestedResource, content);
    }

    private void writeToSocket(Socket socket, String httpRequest) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.write(httpRequest);
        printWriter.flush();
    }
}
