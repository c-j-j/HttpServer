package http;

import http.logging.Logger;
import http.request.RequestProcessor;
import http.request.handlers.AuthenticationRequestWrapper;
import http.request.handlers.LogRequestWrapper;
import http.request.handlers.PartialContentRequestWrapper;
import http.request.handlers.RequestRouter;
import http.request.parsing.RequestParser;
import http.resource.Resource;
import http.resource.ResourceRepository;
import http.response.serializers.ResponseSerializer;
import http.response.ResponseWriter;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int THREAD_POOL_SIZE = 200;
    private final File baseDirectory;
    private final int portNumber;
    private Set<Resource> resources;
    private ExecutorService threadpool;

    public HttpServer(Set<Resource> resources, File baseDirectory, int portNumber) {
        this.baseDirectory = baseDirectory;
        this.portNumber = portNumber;
        this.resources = resources;
        threadpool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void start() {
        Logger logger = new Logger(new File(baseDirectory, "logs"));
        RequestProcessor requestProcessor = buildRequestConsumer(logger);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                waitAndProcessRequests(logger, requestProcessor, serverSocket);
            }
        } catch (IOException e) {
            logger.accept(e.getMessage());
        }
    }

    private void waitAndProcessRequests(Logger logger, RequestProcessor requestProcessor, ServerSocket serverSocket) {
        try {
            submitRequestToThreadpool(requestProcessor, waitForConnection(serverSocket));
        } catch (RuntimeException e) {
            logger.accept(e.getMessage());
        }
    }

    private Socket waitForConnection(ServerSocket serverSocket) {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private RequestProcessor buildRequestConsumer(Logger logger) {
        return new RequestProcessor(new RequestParser(), buildResponseResolver(logger), new ResponseWriter(new ResponseSerializer()));
    }

    private LogRequestWrapper buildResponseResolver(Logger logger) {
        return new LogRequestWrapper(logger, buildAuthRequestWrapper());
    }

    private AuthenticationRequestWrapper buildAuthRequestWrapper() {
        return new AuthenticationRequestWrapper(buildPartialContentWrapper());
    }

    private PartialContentRequestWrapper buildPartialContentWrapper() {
        return new PartialContentRequestWrapper(buildRequestRouter());
    }

    private RequestRouter buildRequestRouter() {
        return new RequestRouter(new ResourceRepository(resources), baseDirectory);
    }

    private void submitRequestToThreadpool(RequestProcessor requestProcessor, Socket socket) {
        threadpool.submit(() -> requestProcessor.accept(socket));
    }
}
