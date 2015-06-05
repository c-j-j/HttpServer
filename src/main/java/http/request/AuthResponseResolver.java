package http.request;

import http.Request;
import http.Response;

import java.util.function.Function;

public class AuthResponseResolver implements Function<Request, Response> {

    private final Function<Request, Response> responseResolver;

    public AuthResponseResolver(Function<Request, Response> responseResolver){
        this.responseResolver = responseResolver;
    }

    @Override
    public Response apply(Request request) {
        return responseResolver.apply(request);
    }
}
