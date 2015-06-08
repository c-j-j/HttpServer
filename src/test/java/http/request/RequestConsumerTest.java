package http.request;

import http.fakes.FakeSocket;
import http.fakes.SpyFunction;
import http.request.builder.RequestBuilder;
import http.request.builder.RequestHeaderBuilder;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.response.builders.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestConsumerTest {

    private FakeSocket fakeSocket;
    private RequestConsumer requestConsumer;
    private SpyFunction<Request, Response> requestHandler;
    private TestBiConsumer<Socket, Response> socketWriter;
    private Response response;
    private Request request;
    private Function<Socket, Request> requestParser;

    @Before
    public void setUp() throws Exception {
        response = new ResponseBuilder().build();
        requestHandler = new SpyFunction<>(response);
        fakeSocket = new FakeSocket();
        socketWriter = new TestBiConsumer<>();
        request = new RequestBuilder().withHeader(new RequestHeaderBuilder().build()).build();
        requestParser = new SpyFunction<>(request);
        requestConsumer = new RequestConsumer(requestParser, requestHandler, socketWriter);
    }

    @Test
    public void closesSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(fakeSocket.hasBeenClosed());
    }

    @Test
    public void getsResponseFromRequestHandler() {
        requestConsumer.accept(fakeSocket);
        assertThat(requestHandler.wasCalledWith()).isEqualTo(request);
    }

    @Test
    public void writesResponseToSocket(){
        requestConsumer.accept(fakeSocket);
        assertThat(socketWriter.calledWithFirstParameter()).isEqualTo(fakeSocket);
        assertThat(socketWriter.calledWithSecondParameter()).isEqualTo(response);
    }

    @Test
    public void returnsErrorResponseWhenExceptionOccurs(){
        RequestConsumer requestConsumer = new RequestConsumer(requestParser, request1 -> {
            throw new RuntimeException();
        }, socketWriter);

        requestConsumer.accept(fakeSocket);
        assertThat(socketWriter.calledWithSecondParameter().getStatusCode()).isEqualTo(HTTPStatusCode.INTERNAL_SERVER_ERROR);
    }

    private class TestBiConsumer<T, U> implements BiConsumer<T, U> {

        private T firstParameter;
        private U secondParameter;

        @Override
        public void accept(T firstParameter, U secondParameter) {
           this.firstParameter = firstParameter;
            this.secondParameter = secondParameter;
        }

        public T calledWithFirstParameter() {
            return firstParameter;
        }

        public U calledWithSecondParameter() {
            return secondParameter;
        }
    }
}