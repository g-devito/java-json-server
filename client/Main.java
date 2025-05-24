package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] database = new String[1000];
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            int index;
            String value;

            if (input.isEmpty()) continue;

            String[] inputArray = input.split(" ", 3);
            String verb = inputArray[0];

            switch (verb) {
                case "set":
                    if (inputArray.length != 3) {
                        System.out.println("ERROR");
                        continue;
                    }
                    index = Integer.parseInt(inputArray[1]);
                    value = inputArray[2];
                    if (index > 1000)
                        System.out.println("ERROR");
                    else {
                        database[index] = value;
                        System.out.println("OK");
                    }
                    break;
                case "get":
                    if (inputArray.length != 2) {
                        System.out.println("ERROR");
                        continue;
                    }
                    index = Integer.parseInt(inputArray[1]);
                    if (index > 1000 || database[index] == null)
                        System.out.println("ERROR");
                    else
                        System.out.println(database[index]);
                    break;
                case "delete":
                    if (inputArray.length != 2) {
                        System.out.println("ERROR");
                        continue;
                    }
                    index = Integer.parseInt(inputArray[1]);
                    if (index > 1000)
                        System.out.println("ERROR");
                    else {
                        database[index] = null;
                        System.out.println("OK");
                    }
                    break;
                case "exit":
                    if (inputArray.length != 1) {
                        System.out.println("ERROR");
                        continue;
                    }
                    return;
                default:
                    System.out.printf("wrong verb: %s%n", verb);
            }
        }
    }
}
