package http.resource;

import builders.RequestHeaderBuilder;
import http.HTTPStatusCode;
import http.RequestHeader;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedirectResourceTest {

    private static final RequestHeader REQUEST_HEADER = new RequestHeaderBuilder().build();
    private RedirectResource redirectResource;

    @Before
    public void setUp() throws Exception {
        redirectResource = new RedirectResource();
    }

    @Test
    public void yields302StatusCode(){
        assertThat(redirectResource.redirect(REQUEST_HEADER).getStatusCode())
                .isEqualTo(HTTPStatusCode.FOUND);
    }

    @Test
    public void hasLocationHeader(){
        assertThat(redirectResource.redirect(REQUEST_HEADER).getLocation())
                .isEqualTo("http://localhost:5000/");
    }
}