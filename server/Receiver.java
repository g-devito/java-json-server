package server;

import com.google.gson.JsonObject;

public class Receiver {
    private static final JsonObject database = new JsonObject();

    public String set(String key, String value) {
        database.addProperty(key, value);
        return "{ \"response\": \"OK\" }";
    }

    public String get(String key) {
                if (!database.has(key)) {
                    return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
                }
                return String.format(
                        "{\"response\":\"OK\",\"value\":\"%s\"}",
                        database.get(key).getAsString());
    }

    public String delete(String key) {
        if (!database.has(key)) {
            return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
        }
        database.remove(key);
        return "{ \"response\": \"OK\" }";
    }
}
