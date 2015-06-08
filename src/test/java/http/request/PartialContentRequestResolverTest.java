package http.request;

import builders.ResponseBuilder;
import http.Response;
import http.request.builder.RequestBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PartialContentRequestResolverTest {

    @Test
    public void callsWrappedResolverWhenNoRangeGiven(){
        Response wrappedResponse = new ResponseBuilder().build();
        Response response = new PartialContentRequestResolver(r -> wrappedResponse).apply(new RequestBuilder().build());
        assertThat(response).isEqualTo(wrappedResponse);
    }
}