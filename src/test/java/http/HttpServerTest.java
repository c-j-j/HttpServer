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

public class HttpServerTest {

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
    public void tearDown(){
        threadPool.shutdown();
    }

    @Test
    public void getsFile() throws IOException {
        threadPool.submit(() -> new HttpServer(baseDirectory, 5001).start());

        File requestedResource = new File(baseDirectory, "some_file");
        FileUtils.writeStringToFile(requestedResource, "Hello World!");
        Socket socket = new Socket("localhost", 5001);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.write(new HTTPRequestBuilder().withAction(HTTPAction.GET).withPath("/some_file").build());
        printWriter.flush();

        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(socket.getInputStream(), stringWriter);
        socket.close();
        System.out.println(stringWriter.toString());
    }
}
