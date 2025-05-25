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
        Map<String, String> flags = parseFlags(args);

        String cmdType = flags.get("-t");
        String index = flags.get("-i");
        String message = flags.get("-m");

        if (cmdType == null) {
            System.out.println("Missing required -t <type> argument.");
            return;
        }

        String msg;
        switch (cmdType) {
            case "get" -> {
                if (index == null) {
                    System.out.println("Missing required -i <index> for 'get'");
                    return;
                }
                msg = String.format("get %s", index);
            }
            case "set" -> {
                if (index == null || message == null) {
                    System.out.println("Missing required -i <index> and/or -m <message> for 'set'");
                    return;
                }
                msg = String.format("set %s %s", index, message);
            }
            case "delete" -> {
                if (index == null) {
                    System.out.println("Missing required -i <index> for 'delete'");
                    return;
                }
                msg = String.format("delete %s", index);
            }
            case "exit" -> msg = "exit";
            default -> {
                System.out.println("Invalid command type: " + cmdType);
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
        for (int i = 0; i < args.length - 1; i+=2)
                map.put(args[i], args[i + 1]);
        return map;
    }
}
