package Chatting;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//handle only the connection and all the input streams and output streams at the Client Handler Thread
public class Server {

    //server side need to declare the Server Socket and the socket
    private Socket client = null;
    private ServerSocket server= null;

    //passing the printWriter alone is understandable but passing it as a list is actually insane cause i don't know jack shit
    //need to change this even though it works
    private List<PrintWriter> clientWriters = Collections.synchronizedList(new ArrayList<>());

    public Server(int port) throws IOException {


        server = new ServerSocket(port);
        server.setReuseAddress(true);
        System.out.println("server is starting");
        System.out.println("waiting for client");

        //accepting the client
        while(true) {
            client = server.accept();

            //change this part to increase client1, client2 like that
            //don't need to do that.
            System.out.println("New Client Connected"+ client.getInetAddress().getHostAddress());

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            clientWriters.add(out);

            //creating new thread object
            //I don't really think that you need to pass the socket client to the thread
            ClientHandler clientSock = new ClientHandler(client, clientWriters);

            //this thread handle the client separately
            new Thread(clientSock).start();
        }

    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);

    }

}

