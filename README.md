# ChatAppForNetworkingClass
My networking professor wants us to make a chat app, but refuses to teach us how to do it, so I'm uploading my code here to maybe help someone else.

Basic Explanation:

  # Server Side:
  
Server Initialization:

The server initializes a ServerSocket object on a specific port (in this case, port 12345) to listen for incoming client connections.
It enters a loop where it continuously accepts client connections using serverSocket.accept().

Client Handling:

For each client connection accepted, the server creates a new ClientHandler object, passing the client's socket to it.
The ClientHandler class extends Thread, allowing each client to be handled concurrently.

ClientHandler Thread:

Each ClientHandler thread handles communication with a single client.
It opens input and output streams (BufferedReader and PrintWriter) to communicate with the client over the network.

Message Broadcasting:

When a message is received from a client, the server broadcasts it to all connected clients by calling the broadcastMessage() method.
The broadcastMessage() method iterates over all ClientHandler objects and sends the message to each client using the sendMessage() method.

  # Client Side:
  
Client Initialization:

The client connects to the server using a Socket object, specifying the server's hostname/IP address and port number.
It opens input and output streams to communicate with the server (BufferedReader for reading server messages, PrintWriter for sending messages to the server).

User Interaction Loop:

The client enters a loop where it continuously prompts the user to enter a message.
It reads the user's input from the console (System.in).

Sending Messages:

When the user enters a message, the client sends it to the server by writing it to the output stream (out.println(message)).

Receiving Messages:

The client starts a new thread to continuously read messages from the server in the background.
This background thread reads messages from the input stream (in.readLine()) and prints them to the console.

How Messages are Handled:

When a client sends a message, the server receives it in the corresponding ClientHandler thread.
The server then broadcasts the message to all connected clients, including the client that sent the message.
Each client, including the sender, receives the broadcasted message and displays it in the console.

Key Concepts:

Sockets: Facilitate communication between the server and clients over the network.

Threads: Enable concurrent handling of multiple clients on the server.

Input/Output Streams: Allow data transfer between the server, clients, and the console.

Looping: Continuous loops in both server and client allow for ongoing interaction.
