package http.builders;

import http.HTTPAction;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;

public class HTTPRequestBuilder {

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
