package server;

public class Request {
    private final String type;
    private final String key;
    private final String value;

    public Request(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public String getKey() {
        return key;
    }
    public String getType() {
        return type;
    }
}
