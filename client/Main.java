package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
//import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        String[] database = new String[1000];
//        Scanner scanner = new Scanner(System.in);
        String address = "127.0.0.1";
        int port = 23456;
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String msg = "Give me a record # 12";
            System.out.println("Client started!");
            output.writeUTF(msg);
            System.out.printf("Sent: %s%n", msg);
            System.out.printf("Received: %s%n", input.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        while (true) {
//            String input = scanner.nextLine().trim();
//            int index;
//            String value;
//
//            if (input.isEmpty()) continue;
//
//            String[] inputArray = input.split(" ", 3);
//            String verb = inputArray[0];
//
//            switch (verb) {
//                case "set":
//                    if (inputArray.length != 3) {
//                        System.out.println("ERROR");
//                        continue;
//                    }
//                    index = Integer.parseInt(inputArray[1]);
//                    value = inputArray[2];
//                    if (index > 1000)
//                        System.out.println("ERROR");
//                    else {
//                        database[index] = value;
//                        System.out.println("OK");
//                    }
//                    break;
//                case "get":
//                    if (inputArray.length != 2) {
//                        System.out.println("ERROR");
//                        continue;
//                    }
//                    index = Integer.parseInt(inputArray[1]);
//                    if (index > 1000 || database[index] == null)
//                        System.out.println("ERROR");
//                    else
//                        System.out.println(database[index]);
//                    break;
//                case "delete":
//                    if (inputArray.length != 2) {
//                        System.out.println("ERROR");
//                        continue;
//                    }
//                    index = Integer.parseInt(inputArray[1]);
//                    if (index > 1000)
//                        System.out.println("ERROR");
//                    else {
//                        database[index] = null;
//                        System.out.println("OK");
//                    }
//                    break;
//                case "exit":
//                    if (inputArray.length != 1) {
//                        System.out.println("ERROR");
//                        continue;
//                    }
//                    return;
//                default:
//                    System.out.printf("wrong verb: %s%n", verb);
//            }
//        }
    }
}
