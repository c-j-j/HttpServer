package http.resource;

import http.response.builders.ResponseBuilder;
import http.request.HTTPAction;
import http.response.Response;
import http.request.Request;

import java.util.Map;

public class ParameterResource implements Resource {

    @Endpoint(path = "/parameters", action = HTTPAction.GET)
    public Response parameters(Request request) {
        return new ResponseBuilder()
                .withContent(queryParametersText(request)).build();
    }

    private String queryParametersText(Request request) {
        Map<String, String> queryParameters = request.getHeader().getQueryParameters();

        StringBuilder queryParamText = new StringBuilder();
        for (String queryParamKey : queryParameters.keySet()) {
           queryParamText.append(String.format("%s = %s\n", queryParamKey, queryParameters.get(queryParamKey)));
        }
        return queryParamText.toString();
    }
}
