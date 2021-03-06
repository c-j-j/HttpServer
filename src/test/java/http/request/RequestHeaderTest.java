package http.request;

import http.request.builder.RequestHeaderBuilder;
import http.url.UrlDecode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {

    @Test
    public void parsesPathWithQueryParameters(){
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/somePath?key1=value1").build();
        assertThat(requestHeader.getPath()).isEqualTo("/somePath");
    }

    @Test
    public void parsesPathWithoutQueryParameters(){
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/somePath").build();
        assertThat(requestHeader.getPath()).isEqualTo("/somePath");
    }

    @Test
    public void parsesQueryParameters() {
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/somePath?key1=value1").build();

        assertThat(requestHeader.getQueryParameters()).containsKey("key1");
        assertThat(requestHeader.getQueryParameters()).containsValue("value1");
    }

    @Test
    public void parsesMultipleQueryParameters() {
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/somePath?key1=value1&key2=value2").build();

        assertThat(requestHeader.getQueryParameters()).containsKey("key1");
        assertThat(requestHeader.getQueryParameters()).containsValue("value1");
        assertThat(requestHeader.getQueryParameters()).containsKey("key2");
        assertThat(requestHeader.getQueryParameters()).containsValue("value2");
    }

    @Test
    public void decodesQueryParameters(){
        String encodedValue = "hello%20world";
        RequestHeader requestHeader = new RequestHeaderBuilder().withURI("/somePath?key1=" + encodedValue).build();
        assertThat(requestHeader.getQueryParameters()).containsValue(UrlDecode.decode("hello world"));
    }
}