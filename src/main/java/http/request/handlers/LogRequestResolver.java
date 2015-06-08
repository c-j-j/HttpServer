package http.request.handlers;

import http.response.Response;
import http.request.Request;

import java.util.function.Consumer;
import java.util.function.Function;

public class LogRequestResolver implements Function<Request, Response> {

    private final Consumer<String> logger;
    private final Function<Request, Response> wrappedResolver;

    public LogRequestResolver(Consumer<String> logger, Function<Request, Response> wrappedResolver) {
        this.logger = logger;
        this.wrappedResolver = wrappedResolver;
    }

    @Override
    public Response apply(Request request) {
        logger.accept(request.getPayload());
        return wrappedResolver.apply(request);
    }
}
