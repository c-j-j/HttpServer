package http;

import http.logging.Logger;
import http.request.handlers.AuthResponseResolver;
import http.request.handlers.LogRequestResolver;
import http.request.handlers.PartialContentRequestResolver;
import http.request.parsing.RequestParser;
import http.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final File baseDirectory;
    private final int portNumber;
    private Set<Resource> resources;
    private ExecutorService threadpool;

    public HttpServer(Set<Resource> resources, File baseDirectory, int portNumber) {
        this.baseDirectory = baseDirectory;
        this.portNumber = portNumber;
        this.resources = resources;
        threadpool = Executors.newFixedThreadPool(200);
    }

    public void start() {
        Logger logger = new Logger(new File(baseDirectory, "logs"));
        RequestConsumer requestConsumer = buildRequestConsumer(logger);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    submitRequestToThreadpool(requestConsumer, socket);
                } catch (IOException e) {
                    logger.accept(e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.accept(e.getMessage());
        }
    }

    private RequestConsumer buildRequestConsumer(Logger logger) {
        return new RequestConsumer(buildResponseResolver(logger), new SocketWriter(new ResponseSerializer()), new RequestParser());
    }

    private LogRequestResolver buildResponseResolver(Logger logger) {
        return new LogRequestResolver(logger, new AuthResponseResolver(new PartialContentRequestResolver(new ResponseGenerator(new ResourceRepository(resources), baseDirectory))));
    }

    private void submitRequestToThreadpool(RequestConsumer requestConsumer, Socket socket) {
        threadpool.submit(() -> requestConsumer.accept(socket));
    }
}
