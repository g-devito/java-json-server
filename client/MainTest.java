package client;

import server.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<Request> requests = new ArrayList<>();

        // 1. Set values
        requests.add(new Request("set", "username", "alice"));
        requests.add(new Request("set", "email", "alice@example.com"));
        requests.add(new Request("set", "age", "30"));

        // 2. Get values (after they have been set)
        requests.add(new Request("get", "username", null));
        requests.add(new Request("get", "email", null));
        requests.add(new Request("get", "age", null));

        // 3. Delete a value
        requests.add(new Request("delete", "age", null));

        // 4. Get a value again after deletion (will return error, but still valid input)
        requests.add(new Request("get", "age", null));

        // 5. Exit
        requests.add(new Request("exit", "", null));

        requests.forEach((request -> {
            String msg;
            String type = request.getType();
            String value = request.getValue();
            String key = request.getKey();
            switch (type) {
                case "get" -> {
                    if (key == null) {
                        System.out.println("Missing required -k <key> for 'get'");
                        return;
                    }
                    msg = String.format("{\"type\":\"get\",\"key\":\"%s\"}", key);

                }
                case "set" -> {
                    if (key == null || value == null) {
                        System.out.println("Missing required -k <key> and/or -v <value> for 'set'");
                        return;
                    }
                    msg = String.format(
                            "{\"type\":\"set\",\"key\":\"%s\",\"value\":\"%s\"}",
                            key,
                            value
                    );
                }
                case "delete" -> {
                    if (key == null) {
                        System.out.println("Missing required -k <key> for 'delete'");
                        return;
                    }
                    msg = String.format(
                            "{\"type\":\"delete\",\"key\":\"%s\"}",
                            key
                    );
                }
                case "exit" -> msg = "{\"type\":\"exit\"}";
                default -> {
                    System.out.println("Invalid command type: " + type);
                    return;
                }
            }

            String address = "127.0.0.1";
            int port = 23456;

            try (
                    Socket socket = new Socket(InetAddress.getByName(address), port);
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                System.out.println("Client started!");
                output.writeUTF(msg);
                System.out.printf("Sent: %s%n", msg);
                System.out.printf("Received: %s%n", input.readUTF());
            } catch (IOException e) {
                throw new RuntimeException("Client error: " + e.getMessage(), e);
            }
        }));
    }
}
