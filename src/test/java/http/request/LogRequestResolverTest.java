package http.request;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import http.Response;
import http.request.builder.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class LogRequestResolverTest {


    private LogRequestResolver logRequestResolver;
    private Response response;
    private SpyConsumer<String> logger;
    private Request request;

    @Before
    public void setUp(){
        response = new ResponseBuilder().build();
        Function<Request, Response> wrappedResolver = r -> response;
        logger = new SpyConsumer<>();
        logRequestResolver = new LogRequestResolver(logger, wrappedResolver);
        RequestHeader requestHeader = new RequestHeaderBuilder().withRequestPayload("requestPayload").build();
        request = new RequestBuilder().withHeader(requestHeader).build();
    }

    @Test
    public void callsWrappedResolver(){
        assertThat(logRequestResolver.apply(request)).isEqualTo(response);
    }

    @Test
    public void logsRequest(){
        logRequestResolver.apply(request);
        assertThat(logger.wasCalledWith()).isEqualTo(request.getPayload());
    }

    private class SpyConsumer<T> implements Consumer<T> {

        private T calledWith;

        @Override
        public void accept(T t) {
           calledWith = t;
        }

        public T wasCalledWith() {
            return calledWith;
        }
    }
}