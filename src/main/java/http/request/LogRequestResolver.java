package http.request;

import http.RequestHeader;
import http.Response;

import java.util.function.Consumer;
import java.util.function.Function;

public class LogRequestResolver implements Function<RequestHeader, Response> {

    private final Consumer<String> logger;
    private final Function<RequestHeader, Response> wrappedResolver;

    public LogRequestResolver(Consumer<String> logger, Function<RequestHeader, Response> wrappedResolver) {
        this.logger = logger;
        this.wrappedResolver = wrappedResolver;
    }

    @Override
    public Response apply(RequestHeader requestHeader) {
        logger.accept(requestHeader.getPayload());
        return wrappedResolver.apply(requestHeader);
    }
}
