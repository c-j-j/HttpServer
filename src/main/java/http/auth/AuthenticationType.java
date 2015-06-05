package http.auth;

public enum AuthenticationType {
    BASIC("Basic");

    private final String description;

    AuthenticationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
