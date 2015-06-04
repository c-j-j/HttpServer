package http;

public enum ContentType {
    JPEG("image/jpeg"),
    PLAIN("text/plain");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
