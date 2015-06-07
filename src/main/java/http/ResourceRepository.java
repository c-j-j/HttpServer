package http;

import http.request.Request;
import http.resource.Endpoint;
import http.resource.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public class ResourceRepository {
    private final Set<Resource> resources;

    public ResourceRepository(Set<Resource> resources) {
        this.resources = Collections.unmodifiableSet(resources);
    }

    public boolean canRespond(Request request) {
        return doesValidResourceExists(request);
    }

    public Response getResponse(Request request) {
        Resource resourceForRequest = findResourceForRequest(request);
        return invokeEndpointMethod(request, resourceForRequest, findEndpointMethod(request, resourceForRequest));
    }

    private Method findEndpointMethod(Request request, Resource resourceForRequest) {
        return getMethods(resourceForRequest)
                .filter(m -> isMethodAMatchingEndpoint(request, m))
                .findFirst()
                .get();
    }

    private Resource findResourceForRequest(Request request) {
        return resources.stream()
                .filter(r -> doesResourceContainMatchingEndpoint(request, r))
                .findFirst()
                .get();
    }

    private Response invokeEndpointMethod(Request validRequest, Resource resource, Method method) {
        try {
            return (Response) method.invoke(resource, validRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMethodAMatchingEndpoint(Request validRequest, Method method) {
        return isMethodAnEndpoint(method) && doesEndpointMatchRequest(validRequest, method);
    }

    private boolean doesValidResourceExists(Request request) {
        return resources
                .stream()
                .anyMatch(resource -> doesResourceContainMatchingEndpoint(request, resource));
    }

    private boolean doesResourceContainMatchingEndpoint(Request request, Resource resource) {
        return getMethods(resource).anyMatch(m -> isMethodAMatchingEndpoint(request, m));
    }

    private Stream<Method> getMethods(Resource resource) {
        return Arrays.stream(resource.getClass().getDeclaredMethods());
    }

    private boolean isMethodAnEndpoint(Method declaredMethod) {
        return declaredMethod.isAnnotationPresent(Endpoint.class);
    }

    private boolean doesEndpointMatchRequest(Request request, Method declaredMethod) {
        Endpoint annotation = declaredMethod.getAnnotation(Endpoint.class);
        return annotation.path().equals(request.getPath())
                && annotation.action() == request.getAction();
    }

}
