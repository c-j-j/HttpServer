import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private RequestParser requestParser;

    @Before
    public void setUp() throws Exception {
        requestParser = new RequestParser();
    }

    @Test
    public void parsesGETAction() {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.GET).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void parsesPOSTAction() throws IOException {
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withAction(HTTPAction.POST).build()));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.POST);
    }

    @Test
    public void parsesPath(){
        Socket socket = new FakeSocket(inputStream(new HTTPRequestBuilder().withPath("/SomePath").build()));
        assertThat(requestParser.apply(socket).getPath()).isEqualTo("/SomePath");
    }

    private InputStream inputStream(String httpRequest) {
        return IOUtils.toInputStream(httpRequest);
    }

    private class HTTPRequestBuilder {

        private HTTPAction action = HTTPAction.GET;
        private String path;

        public HTTPRequestBuilder withAction(HTTPAction action){
            this.action = action;
            return this;
        }

        public HTTPRequestBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public String build() {
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();
            Template template = velocityEngine.getTemplate("templates/http_request.vm");
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("action", action);
            velocityContext.put("path", path);

            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            return stringWriter.toString();
        }

    }
}