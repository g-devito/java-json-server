package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String msg;
        Map<String, String> flags = parseFlags(args);

        String key = flags.get("-k");
        String type = flags.get("-t");
        String value = flags.get("-v");

        if (type == null) {
            System.out.println("Missing required -t <type> argument.");
            return;
        }


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
    }

    private static Map<String, String> parseFlags(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2)
            map.put(args[i], args[i + 1]);
        return map;
    }
}
