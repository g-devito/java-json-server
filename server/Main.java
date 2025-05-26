package server;

import com.google.gson.Gson;

public class Main {
    static Gson gson = new Gson();

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        Server server = new Server(address, port);
        server.start();
    }
}
