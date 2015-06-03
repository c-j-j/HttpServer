package http;

import http.fakes.TestFunction;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestConsumerTest {

    private FakeSocket fakeSocket;
    private RequestConsumer requestConsumer;
    private TestFunction<Socket, Response> responseGenerator;
    private TestBiConsumer<Socket, Response> socketWriter;
    private Response response;

    @Before
    public void setUp() throws Exception {
        response = new Response();
        responseGenerator = new TestFunction<>(response);
        fakeSocket = new FakeSocket();
        socketWriter = new TestBiConsumer<>();
        requestConsumer = new RequestConsumer(responseGenerator, socketWriter);
    }

    @Test
    public void ClosesSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(fakeSocket.hasBeenClosed());
    }

    @Test
    public void CreatesResponseFromSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(responseGenerator.wasCalledWith()).isEqualTo(fakeSocket);
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