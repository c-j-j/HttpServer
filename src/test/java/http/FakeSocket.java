package http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {
    private boolean closed = false;
    private final OutputStream outputStream;
    private final InputStream inputStream;

    public FakeSocket(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public FakeSocket(OutputStream outputStream) {
        this(new ByteArrayInputStream("".getBytes()), outputStream);
    }

    public FakeSocket(InputStream inputStream) {
        this(inputStream, new ByteArrayOutputStream());
    }

    public FakeSocket() {
        this(new ByteArrayOutputStream());
    }

    public void close() {
        closed = true;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream(){
        return inputStream;
    }

    public boolean hasBeenClosed() {
        return closed;
    }
}
