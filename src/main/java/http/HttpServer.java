package http;

import http.config.Configuration;
import http.logging.Logger;
import http.request.RequestProcessor;
import http.request.auth.Authenticator;
import http.request.handlers.AuthenticationRequestWrapper;
import http.request.handlers.LogRequestWrapper;
import http.request.handlers.PartialContentRequestWrapper;
import http.request.handlers.RequestRouter;
import http.request.parsing.RequestParser;
import http.resource.Resource;
import http.resource.ResourceRepository;
import http.response.ResponseWriter;
import http.response.serializers.ResponseSerializer;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class HttpServer {
    private static final int THREAD_POOL_SIZE = 200;
    private Set<Resource> resources;
    private ExecutorService threadPool;
    private Configuration configuration;
    private final Supplier<Boolean> shutdownLatch;

    public HttpServer(Set<Resource> resources, Configuration configuration) {
        this(resources, configuration, ()->true);
    }

    public HttpServer(Set<Resource> resources, Configuration configuration, Supplier<Boolean> shutdownLatch) {
        this.resources = resources;
        this.configuration = configuration;
        this.shutdownLatch = shutdownLatch;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void start() {
        Logger logger = new Logger(new File(configuration.getBaseDirectory(), "logs"));
        RequestProcessor requestProcessor = buildRequestConsumer(logger);
        printDirectoryBeingServed();
        try (ServerSocket serverSocket = new ServerSocket(configuration.getPort())) {
            listenForIncomingRequests(logger, requestProcessor, serverSocket);
        } catch (IOException e) {
            logger.accept(e.getMessage());
        }
    }

    private void listenForIncomingRequests(Logger logger, RequestProcessor requestProcessor, ServerSocket serverSocket) {
        while (continueRunning()) {
            printPortBeingListenedTo();
            waitAndProcessRequests(logger, requestProcessor, serverSocket);
        }
    }

    private Boolean continueRunning() {
        return shutdownLatch.get();
    }

    private void waitAndProcessRequests(Logger logger, RequestProcessor requestProcessor, ServerSocket serverSocket) {
        try {
            submitRequestToThreadPool(requestProcessor, waitForConnection(serverSocket));
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
        return new AuthenticationRequestWrapper(buildPartialContentWrapper(), new Authenticator(configuration));
    }

    private PartialContentRequestWrapper buildPartialContentWrapper() {
        return new PartialContentRequestWrapper(buildRequestRouter());
    }

    private RequestRouter buildRequestRouter() {
        return new RequestRouter(new ResourceRepository(resources), configuration.getBaseDirectory());
    }

    private void submitRequestToThreadPool(RequestProcessor requestProcessor, Socket socket) {
        threadPool.submit(() -> requestProcessor.accept(socket));
    }

    private void printPortBeingListenedTo() {
        System.out.println(String.format("Listening on port %d", configuration.getPort()));
    }

    private void printDirectoryBeingServed() {
        System.out.println(String.format("Serving Files From %s", configuration.getBaseDirectory()));
    }
}
