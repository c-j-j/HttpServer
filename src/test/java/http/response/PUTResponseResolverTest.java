package http.response;

import builders.RequestBuilder;
import http.HTTPStatusCode;
import http.Response;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class PUTResponseResolverTest {

    @Test
    public void yields200Status() {
        Response response = new PUTResponseResolver().getResponse(new File(""), new RequestBuilder().build());
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }
}