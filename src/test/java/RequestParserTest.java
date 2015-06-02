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
    public void ParsesGETAction() {
        Socket socket = new FakeSocket(inputStream(createHTTPRequest(HTTPAction.GET)));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.GET);
    }

    @Test
    public void ParsesPOSTAction() throws IOException {
        Socket socket = new FakeSocket(inputStream(createHTTPRequest(HTTPAction.POST)));
        assertThat(requestParser.apply(socket).getAction()).isEqualTo(HTTPAction.POST);
    }

    private InputStream inputStream(String httpRequest) {
        return IOUtils.toInputStream(httpRequest);
    }

    private String createHTTPRequest(HTTPAction action) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        Template template = velocityEngine.getTemplate("templates/http_request.vm");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("Action", action);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        return stringWriter.toString();
    }
}