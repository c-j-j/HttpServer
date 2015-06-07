package http;

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

    public boolean canRespond(RequestHeader requestHeader) {
        return doesValidResourceExists(requestHeader);
    }

    public Response getResponse(RequestHeader requestHeader) {
        Resource resourceForRequest = findResourceForRequest(requestHeader);
        return invokeEndpointMethod(requestHeader, resourceForRequest, findEndpointMethod(requestHeader, resourceForRequest));
    }

    private Method findEndpointMethod(RequestHeader requestHeader, Resource resourceForRequest) {
        return getMethods(resourceForRequest)
                .filter(m -> isMethodAMatchingEndpoint(requestHeader, m))
                .findFirst()
                .get();
    }

    private Resource findResourceForRequest(RequestHeader requestHeader) {
        return resources.stream()
                .filter(r -> doesResourceContainMatchingEndpoint(requestHeader, r))
                .findFirst()
                .get();
    }

    private Response invokeEndpointMethod(RequestHeader validRequestHeader, Resource resource, Method method) {
        try {
            return (Response) method.invoke(resource, validRequestHeader);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMethodAMatchingEndpoint(RequestHeader validRequestHeader, Method method) {
        return isMethodAnEndpoint(method) && doesEndpointMatchRequest(validRequestHeader, method);
    }

    private boolean doesValidResourceExists(RequestHeader requestHeader) {
        return resources
                .stream()
                .anyMatch(resource -> doesResourceContainMatchingEndpoint(requestHeader, resource));
    }

    private boolean doesResourceContainMatchingEndpoint(RequestHeader requestHeader, Resource resource) {
        return getMethods(resource).anyMatch(m -> isMethodAMatchingEndpoint(requestHeader, m));
    }

    private Stream<Method> getMethods(Resource resource) {
        return Arrays.stream(resource.getClass().getDeclaredMethods());
    }

    private boolean isMethodAnEndpoint(Method declaredMethod) {
        return declaredMethod.isAnnotationPresent(Endpoint.class);
    }

    private boolean doesEndpointMatchRequest(RequestHeader requestHeader, Method declaredMethod) {
        Endpoint annotation = declaredMethod.getAnnotation(Endpoint.class);
        return annotation.path().equals(requestHeader.getPath())
                && annotation.action() == requestHeader.getAction();
    }

}
