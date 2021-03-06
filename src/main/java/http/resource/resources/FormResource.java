package http.resource.resources;

import http.resource.Endpoint;
import http.resource.Resource;
import http.response.builders.ResponseBuilder;
import http.request.HTTPAction;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;

public class FormResource implements Resource {

    private static final String FORM_PATH = "/form";
    private String postedData = "";

    @Endpoint(path = FORM_PATH, action = HTTPAction.GET)
    public Response get(Request request) {
        return new ResponseBuilder().withBody(postedData).withStatusCode(HTTPStatusCode.OK).build();
    }

    @Endpoint(path = FORM_PATH, action = HTTPAction.POST)
    public Response post(Request request) {
        postedData = request.getBody();
        return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
    }

    @Endpoint(path = FORM_PATH, action = HTTPAction.PUT)
    public Response put(Request request) {
        postedData = request.getBody();
        return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
    }

    @Endpoint(path = FORM_PATH, action = HTTPAction.DELETE)
    public Response delete(Request request) {
        postedData = "";
        return new ResponseBuilder().withStatusCode(HTTPStatusCode.OK).build();
    }
}
