package http.resource;

import http.HTTPStatusCode;
import http.response.Response;
import http.request.Request;
import http.request.builder.RequestBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FormResourceTest {

    private Request request = new RequestBuilder().build();
    private FormResource formResource = new FormResource();

    @Test
    public void getReturns200(){
        Response response = formResource.get(request);
        assertThat(response.getStatusCode()).isEqualTo(HTTPStatusCode.OK);
    }

    @Test
    public void postSetsData(){
        Response postResponse = formResource.post(new RequestBuilder().withBody("someBody").build());
        assertThat(postResponse.getStatusCode()).isEqualTo(HTTPStatusCode.OK);

        Response getResponse = formResource.get(request);
        assertThat(getResponse.getContentsAsString()).isEqualTo("someBody");
    }

    @Test
    public void putUpdatesData(){
        Response postResponse = formResource.put(new RequestBuilder().withBody("someUpdatedBody").build());
        assertThat(postResponse.getStatusCode()).isEqualTo(HTTPStatusCode.OK);

        Response getResponse = formResource.get(request);
        assertThat(getResponse.getContentsAsString()).isEqualTo("someUpdatedBody");
    }

    @Test
    public void deleteClearsData(){
        Response postResponse = formResource.delete(new RequestBuilder().build());
        assertThat(postResponse.getStatusCode()).isEqualTo(HTTPStatusCode.OK);

        Response getResponse = formResource.get(request);
        assertThat(getResponse.getContentsAsString()).isEmpty();
    }
}