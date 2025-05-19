package org.chatbot.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader userInputReader;

    public ChatClient(String address, int port) throws Exception {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws Exception {
        return in.readLine();
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
        userInputReader.close();
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient("localhost", 1234);

        System.out.println("Connected to chatbot. Type your messages:");
        Scanner input = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Your message: ");
                String userInput = input.nextLine();

                if (userInput == null || userInput.isEmpty()) {
                    break;
                }

                client.send(userInput);
                String response = client.receive();
                System.out.println("Server says: " + response);
            }
        } finally {
            input.close();
            client.close();
        }
    }
}
