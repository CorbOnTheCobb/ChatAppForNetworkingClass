import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Create a server socket on port 12345
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Create a new thread to handle client communication
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler); // Add client handler to the list
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast a message to all connected clients
    public static synchronized void broadcastMessage(String message, int groupNum) {
        for (ClientHandler client : clients) {
          if (client.getGroupNum() == groupNum)
            client.sendMessage(message);
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int groupNum = -1;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public int getGroupNum() {
      // While groupNum is the default value: do nothing. After, return groupNum 
      while(groupNum == -1) {}
      return groupNum;
    }

    public void run() {
        try {
            // Open input and output streams for communication with the client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Parse the integer from the client's first message and store it
            while(in.readLine() == null) {}
            groupNum = Integer.parseInt(in.readLine());

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                // Broadcast the message to all connected clients
                ChatServer.broadcastMessage(message, groupNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the connection
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to send a message to this client
    public void sendMessage(String message) {
        out.println(message);
    }
}

