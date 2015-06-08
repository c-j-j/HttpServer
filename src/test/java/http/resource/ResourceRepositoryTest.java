package http.resource;

import http.request.HTTPAction;
import http.request.Request;
import http.request.builder.RequestBuilder;
import http.request.builder.RequestHeaderBuilder;
import http.response.Response;
import http.response.builders.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceRepositoryTest {

    private ResourceRepository resourceRepository;
    private Request validRequest;

    @Before
    public void setUp() throws Exception {
        resourceRepository = new ResourceRepository(new HashSet<Resource>() {
            {
                add(new FakeResourse());
            }
        });
        validRequest = new RequestBuilder().withHeader(new RequestHeaderBuilder()
                .withHTTPAction(HTTPAction.GET)
                .withURI("/path")
                .build()).build();
    }

    @Test
    public void cannotRespondToRequest() {
        assertThat(resourceRepository.canRespond(new RequestBuilder().withHeader(new RequestHeaderBuilder()
                .withHTTPAction(HTTPAction.POST)
                .withURI("/path")
                .build()).build())).isFalse();
    }

    @Test
    public void canResponseToRequest() {
        assertThat(resourceRepository.canRespond(validRequest)).isTrue();
    }

    @Test
    public void respondsToRequest(){
        Response response = resourceRepository.getResponse(validRequest);
        assertThat(response.getBodyAsString()).isEqualTo("Some Content");
    }

    public class FakeResourse implements Resource {
        @Endpoint(action = HTTPAction.GET, path = "/path")
        public Response fakePath(Request request) {
            return new ResponseBuilder().withBody("Some Content").build();
        }
    }

}