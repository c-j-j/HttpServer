package http;

import http.request.AuthResponseResolver;
import http.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class HttpServer {
    private final File baseDirectory;
    private final int portNumber;
    private Set<Resource> resources;

    public HttpServer(Set<Resource> resources, File baseDirectory, int portNumber) {
        this.baseDirectory = baseDirectory;
        this.portNumber = portNumber;
        this.resources = resources;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                System.out.println("Waiting for connections on port 5000");
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.out.println("Connection received");
                    new RequestConsumer(new AuthResponseResolver(new ResponseGenerator(new ResourceRepository(resources), baseDirectory)), new SocketWriter(new ResponseSerializer()), new RequestParser()).accept(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
