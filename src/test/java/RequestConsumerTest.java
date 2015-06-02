import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestConsumerTest {

    private FakeSocket fakeSocket;
    private RequestConsumer requestConsumer;
    private Request request;
    private Function<Socket, Request> requestCreator;
    private TestFunction<Request, Response> responseGenerator;
    private TestBiConsumer<Socket, Response> socketWriter;
    private Response response;

    @Before
    public void setUp() throws Exception {
        request = new Request();
        requestCreator = s -> request;
        response = new Response();
        responseGenerator = new TestFunction<>(response);
        fakeSocket = new FakeSocket();
        socketWriter = new TestBiConsumer<>();
        requestConsumer = new RequestConsumer(requestCreator, responseGenerator, socketWriter);
    }

    @Test
    public void ClosesSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(fakeSocket.hasBeenClosed());
    }

    @Test
    public void CreatesResponseFromSocket() {
        requestConsumer.accept(fakeSocket);
        assertThat(responseGenerator.wasCalledWith()).isEqualTo(request);
    }

    @Test
    public void WritesResponseToSocket(){
        requestConsumer.accept(fakeSocket);
        assertThat(socketWriter.calledWithFirstParameter()).isEqualTo(fakeSocket);
        assertThat(socketWriter.calledWithSecondParameter()).isEqualTo(response);
    }

    private class FakeSocket extends Socket {
        private boolean closed = false;

        public void close() {
            closed = true;
        }

        public boolean hasBeenClosed() {
            return closed;
        }
    }

    private class TestFunction<T, R> implements Function<T, R> {

        private T calledWith;
        private R stubResponse;

        public TestFunction(R stubResponse) {

            this.stubResponse = stubResponse;
        }

        @Override
        public R apply(T o) {
            calledWith = o;
            return stubResponse;
        }

        public T wasCalledWith()
        {
           return calledWith;
        }
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