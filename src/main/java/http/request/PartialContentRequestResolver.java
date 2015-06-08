package http.request;

import http.Response;

import java.util.function.Function;

public class PartialContentRequestResolver implements Function<Request, Response> {

    private final Function<Request, Response> requestResolver;

    public PartialContentRequestResolver(Function<Request, Response> requestResolver) {
        this.requestResolver = requestResolver;
    }

    @Override
    public Response apply(Request request) {
        return requestResolver.apply(request);
    }
}
