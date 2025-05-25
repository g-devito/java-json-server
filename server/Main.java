package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String[] database = new String[1000];
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
                        continue; // bad client, skip
                    }

                    if (receivedMsg.isEmpty()) {
                        output.writeUTF("ERROR");
                        continue;
                    }

                    String[] inputArray = receivedMsg.split(" ", 3);
                    String command = inputArray[0];

                    switch (command) {
                        case "set": {
                            if (inputArray.length != 3) {
                                output.writeUTF("ERROR");
                                break;
                            }
                            int index = parseIndex(inputArray[1]);
                            String value = inputArray[2];
                            if (indexInvalid(index)) {
                                output.writeUTF("ERROR");
                            } else {
                                database[index] = value;
                                output.writeUTF("OK");
                            }
                            break;
                        }

                        case "get": {
                            if (inputArray.length != 2) {
                                output.writeUTF("ERROR");
                                break;
                            }
                            int index = parseIndex(inputArray[1]);
                            if (indexInvalid(index) || database[index] == null) {
                                output.writeUTF("ERROR");
                            } else {
                                output.writeUTF(database[index]);
                            }
                            break;
                        }

                        case "delete": {
                            if (inputArray.length != 2) {
                                output.writeUTF("ERROR");
                                break;
                            }
                            int index = parseIndex(inputArray[1]);
                            if (indexInvalid(index)) {
                                output.writeUTF("ERROR");
                            } else {
                                database[index] = null;
                                output.writeUTF("OK");
                            }
                            break;
                        }

                        case "exit":
                            output.writeUTF("OK");
                            running = false;
                            break;

                        default:
                            output.writeUTF("ERROR");
                    }
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Converts user index (1-1000) to internal index (0-999)
    private static int parseIndex(String input) {
        try {
            return Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Validates internal index is within array bounds
    private static boolean indexInvalid(int index) {
        return index < 0 || index >= 1000;
    }
}
