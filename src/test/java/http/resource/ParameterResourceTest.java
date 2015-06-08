package http.resource;

import builders.RequestHeaderBuilder;
import http.Response;
import http.request.builder.RequestBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterResourceTest {

    @Test
    public void returnsDecodedParameters(){
        Response response = new ParameterResource().parameters(new RequestBuilder().withHeader(new RequestHeaderBuilder()
                .withURI("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C" +
                        "%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff\n").build()).build());

        assertThat(response.getContentsAsString()).contains("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?");
        assertThat(response.getContentsAsString()).contains("variable_2 = stuff");

    }
}