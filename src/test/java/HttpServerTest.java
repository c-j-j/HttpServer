import org.junit.Test;

import static org.junit.Assert.*;

public class HttpServerTest {

    @Test
    public void delegates() {
        new HttpServer().start();
    }
}