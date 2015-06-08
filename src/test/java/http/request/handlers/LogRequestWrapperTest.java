package http.request.handlers;

import http.request.builder.RequestHeaderBuilder;
import http.response.builders.ResponseBuilder;
import http.response.Response;
import http.request.Request;
import http.request.RequestHeader;
import http.request.builder.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class LogRequestWrapperTest {


    private LogRequestWrapper logRequestWrapper;
    private Response response;
    private SpyConsumer<String> logger;
    private Request request;

    @Before
    public void setUp(){
        response = new ResponseBuilder().build();
        Function<Request, Response> wrappedResolver = r -> response;
        logger = new SpyConsumer<>();
        logRequestWrapper = new LogRequestWrapper(logger, wrappedResolver);
        RequestHeader requestHeader = new RequestHeaderBuilder().withRequestPayload("requestPayload").build();
        request = new RequestBuilder().withHeader(requestHeader).build();
    }

    @Test
    public void callsWrappedResolver(){
        assertThat(logRequestWrapper.apply(request)).isEqualTo(response);
    }

    @Test
    public void logsRequest(){
        logRequestWrapper.apply(request);
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