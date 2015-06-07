package http;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import http.fakes.SpyFunction;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestConsumerTest {

    private FakeSocket fakeSocket;
    private RequestConsumer requestConsumer;
    private SpyFunction<RequestHeader, Response> responseGenerator;
    private TestBiConsumer<Socket, Response> socketWriter;
    private Response response;
    private Function<Socket, String> socketReader;
    private Function<String, RequestHeader> requestDeserializer;
    private Function<Socket, RequestHeader> requestParser;
    private RequestHeader requestHeader;

    @Before
    public void setUp() throws Exception {
        response = new ResponseBuilder().build();
        responseGenerator = new SpyFunction<>(response);
        fakeSocket = new FakeSocket();
        socketWriter = new TestBiConsumer<>();
        requestHeader = new RequestHeaderBuilder().build();
        requestParser = new SpyFunction<>(requestHeader);
        requestConsumer = new RequestConsumer(responseGenerator, socketWriter, requestParser);
    }

    @Test
    public void ClosesSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(fakeSocket.hasBeenClosed());
    }

    @Test
    @Deprecated//TODO remove
    public void CreatesResponseFromSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(responseGenerator.wasCalledWith()).isEqualTo(requestHeader);
    }

    @Test
    public void WritesResponseToSocket(){
        requestConsumer.accept(fakeSocket);
        assertThat(socketWriter.calledWithFirstParameter()).isEqualTo(fakeSocket);
        assertThat(socketWriter.calledWithSecondParameter()).isEqualTo(response);
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