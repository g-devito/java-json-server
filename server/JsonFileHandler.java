package server;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonFileHandler {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private final String filePath = Paths.get(
            System.getProperty("user.dir"),
            "JSON Database with Java",
            "task",
            "src",
            "server",
            "data",
            "db.json").toString();

    public JsonObject readJsonFile() {
        readLock.lock();
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = Objects.requireNonNullElse(JsonParser.parseReader(reader), new JsonObject());
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public void writeJsonFile(JsonObject jsonObject) {
        writeLock.lock();
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
