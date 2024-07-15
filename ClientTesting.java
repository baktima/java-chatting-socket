package Chatting;

import java.util.*;
import java.io.*;
import java.net.*;


public class ClientTesting {
    public static void main(String[] args) throws IOException {

    //client side only need the socket and connect the server echo using stream in and out
    Socket client = new Socket("localhost", 8080);
    System.out.println("connecting to the server");

    //declaring the in and out for the client
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        //you don't have to necessarily use Printwriter just use buffered reader and buffered writer
        //don't make everything extra complicated
        BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        Scanner input = new Scanner(System.in);

        //always Display all the message from the server using multi thread
        AcceptServerMessage acceptServerMessage = new AcceptServerMessage(client);
        new Thread(acceptServerMessage).start();

        String line ="";
        while(!line.equals("exit")) {


            //read the client message
            line = input.nextLine();
            out1.write(line);
            out1.newLine();
            out1.flush();

        }
        client.close();

    }
}
