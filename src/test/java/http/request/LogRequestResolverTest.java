package http.request;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import http.RequestHeader;
import http.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class LogRequestResolverTest {


    private LogRequestResolver logRequestResolver;
    private Response response;
    private RequestHeader requestHeader;
    private SpyConsumer<String> logger;

    @Before
    public void setUp(){
        response = new ResponseBuilder().build();
        Function<RequestHeader, Response> wrappedResolver = r -> response;
        logger = new SpyConsumer<>();
        logRequestResolver = new LogRequestResolver(logger, wrappedResolver);
        requestHeader = new RequestHeaderBuilder().withRequestPayload("requestPayload").build();
    }

    @Test
    public void callsWrappedResolver(){
        assertThat(logRequestResolver.apply(requestHeader)).isEqualTo(response);
    }

    @Test
    public void logsRequest(){
        logRequestResolver.apply(requestHeader);
        assertThat(logger.wasCalledWith()).isEqualTo(requestHeader.getPayload());
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