package http.integration;

import http.HttpServer;
import http.config.builders.ConfigurationBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntegrationTestBase {
    protected static final int PORT = 5001;
    private ExecutorService threadPool;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File baseDirectory;

    @Before
    public void setUp() throws IOException {
        baseDirectory = temporaryFolder.newFolder();
        threadPool = Executors.newFixedThreadPool(2);
        startHTTPServer();
    }

    @After
    public void tearDown() {
        threadPool.shutdown();
    }

    protected void startHTTPServer() {
        threadPool.submit(() -> new HttpServer(Collections.emptySet(), baseDirectory, PORT, new ConfigurationBuilder().build()).start());
    }


    protected String readHTTPResponse(Socket socket) throws IOException {
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(socket.getInputStream(), stringWriter);
        return stringWriter.toString();
    }

    protected void createFile(String filename, String content) throws IOException {
        File requestedResource = new File(baseDirectory, filename);
        FileUtils.writeStringToFile(requestedResource, content);
    }

    protected void sendHTTPRequest(Socket socket, String httpRequest) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.write(httpRequest);
        printWriter.flush();
    }
}
