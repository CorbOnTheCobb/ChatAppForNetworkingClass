import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String name = args[0];
        try {
            Socket socket = new Socket("localhost", 12345); // Connect to the server running on localhost:12345
            System.out.println("Connected to server.");

            // Open input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Open input stream for user input
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Start a thread to continuously read messages from the server
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while (true) {
                // Allow the user to type messages
                //System.out.print("Enter message: ");
                message = name + ": " + userInput.readLine();
                if (message.equalsIgnoreCase("exit")) break; // Exit the loop if the user types "exit"
                
                // Send the message to the server
                out.println(message);
            }

            // Close the connections
            in.close();
            out.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

