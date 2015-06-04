package http.resource;

import builders.RequestBuilder;
import http.HTTPStatusCode;
import http.Request;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedirectResourceTest {

    private static final Request REQUEST = new RequestBuilder().build();
    private RedirectResource redirectResource;

    @Before
    public void setUp() throws Exception {
        redirectResource = new RedirectResource();
    }

    @Test
    public void yields302StatusCode(){
        assertThat(redirectResource.redirect(REQUEST).getStatusCode())
                .isEqualTo(HTTPStatusCode.FOUND);
    }

    @Test
    public void hasLocationHeader(){
        assertThat(redirectResource.redirect(REQUEST).getLocation())
                .isEqualTo("http://localhost:5000/");
    }
}