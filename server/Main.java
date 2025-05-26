package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static JsonObject database = new JsonObject();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        String address = "127.0.0.1";

        int port = 23456;
        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            boolean running = true;

            while (running) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String receivedMsg;
                    try {
                        receivedMsg = input.readUTF();
                    } catch (IOException e) {
                        continue;
                    }

                    if (receivedMsg.isEmpty()) {
                        output.writeUTF("ERROR");
                        continue;
                    }

                    Request request = gson.fromJson(receivedMsg, Request.class);
                    String type = request.getType();
                    String key = request.getKey();

                    switch (type) {
                        case "set": {
                            String value = request.getValue();
                            database.addProperty(key, value);
                            if (database.get(key).getAsString().equals(value))
                                output.writeUTF("{ \"response\": \"OK\" }");
                            break;
                        }

                        case "get": {
                            if (!database.has(key)) {
                                output.writeUTF("{\"response\":\"ERROR\",\"reason\":\"No such key\"}");
                                break;
                            }
                            output.writeUTF(String.format(
                                    "{\"response\":\"OK\",\"value\":\"%s\"}",
                                    database.get(key).getAsString())
                            );
                            break;
                        }

                        case "delete": {
                            if (!database.has(key)) {
                                output.writeUTF("{\"response\":\"ERROR\",\"reason\":\"No such key\"}");
                                break;
                            }
                            database.remove(key);
                            output.writeUTF("{\"response\":\"OK\"}");
                            break;
                        }

                        case "exit": {
                            output.writeUTF("{\"response\":\"OK\"}");
                            running = false;
                            break;
                        }

                        default: {
                            output.writeUTF("{\"response\":\"ERROR\"}");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
