package http;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private final File baseDirectory;
    private final int portNumber;

    public HttpServer(File baseDirectory, int portNumber) {
        this.baseDirectory = baseDirectory;
        this.portNumber = portNumber;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                System.out.println("Waiting for connections on port 5000");
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.out.println("Connection received");
                    new RequestConsumer(new RequestParser(), new ResponseGenerator(baseDirectory), new SocketWriter()).accept(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
