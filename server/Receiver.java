package server;

import com.google.gson.JsonObject;

public class Receiver {
    private static JsonObject database = new JsonObject();
    JsonFileHandler handler = new JsonFileHandler();

    public String set(String key, String value) {
        database = handler.readJsonFile();
        database.addProperty(key, value);
        handler.writeJsonFile(database);
        return "{ \"response\": \"OK\" }";
    }

    public String get(String key) {
        database = handler.readJsonFile();
        if (!database.has(key)) {
            return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
        }
        return String.format(
                "{\"response\":\"OK\",\"value\":\"%s\"}",
                database.get(key).getAsString());
    }

    public String delete(String key) {
        database = handler.readJsonFile();

        if (!database.has(key)) {
            return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
        }
        database.remove(key);
        handler.writeJsonFile(database);
        return "{ \"response\": \"OK\" }";
    }
}
