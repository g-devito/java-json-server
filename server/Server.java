package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static server.Main.gson;

public class Server {
    String address;
    int port;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    private static String processRequest(String receivedMsg) {
        Request request = gson.fromJson(receivedMsg, Request.class);

        Invoker invoker = new Invoker();
        return invoker.process(request);
    }

    private static boolean handleRequest(Socket socket) {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String receivedMsg;
            try {
                receivedMsg = input.readUTF();
            } catch (IOException e) {
                return true;
            }

            String response = processRequest(receivedMsg);
            if (response.equals("exit")) {
                response = "{ \"response\": \"OK\" }";
                output.writeUTF(response);
                return false;
            }
            output.writeUTF(response);

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(this.port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            AtomicBoolean running = new AtomicBoolean(true);

            while (running.get()) {
                Socket socket = server.accept();
                executor.submit(() -> running.set(handleRequest(socket)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
