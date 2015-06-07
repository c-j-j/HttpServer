package http;

import builders.RequestHeaderBuilder;
import builders.ResponseBuilder;
import http.resource.Endpoint;
import http.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceRepositoryTest {

    private ResourceRepository resourceRepository;
    private RequestHeader validRequestHeader;

    @Before
    public void setUp() throws Exception {
        resourceRepository = new ResourceRepository(new HashSet<Resource>() {
            {
                add(new FakeResourse());
            }
        });
        validRequestHeader = new RequestHeaderBuilder()
                .withHTTPAction(HTTPAction.GET)
                .withPath("/path")
                .build();
    }

    @Test
    public void cannotRespondToRequest() {
        assertThat(resourceRepository.canRespond(new RequestHeaderBuilder()
                .withHTTPAction(HTTPAction.POST)
                .withPath("/path")
                .build())).isFalse();
    }

    @Test
    public void canResponseToRequest() {
        assertThat(resourceRepository.canRespond(validRequestHeader)).isTrue();
    }

    @Test
    public void respondsToRequest(){
        Response response = resourceRepository.getResponse(validRequestHeader);
        assertThat(response.getContentsAsString()).isEqualTo("Some Content");
    }

    private class FakeResourse implements Resource {


        @Endpoint(action = HTTPAction.GET, path = "/path")
        public Response fakePath(RequestHeader requestHeader) {
            return new ResponseBuilder().withContent("Some Content").build();
        }
    }

}