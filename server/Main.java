package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started, waiting for client...");
            try (Socket socket = server.accept();
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                System.out.println("Client connected!");

                String receivedMsg = input.readUTF();
                System.out.printf("Received: %s%n", receivedMsg);

                String responseMsg = "A record # 12 was sent!";
                System.out.printf("Sent: %s%n", responseMsg);

                output.writeUTF(responseMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
