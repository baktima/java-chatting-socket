package Chatting;

import java.util.*;
import java.io.*;
import java.net.*;

public class AcceptServerMessage implements Runnable{

    //accepting the socket that is passed on from the client testing
    private final Socket client;
    public AcceptServerMessage(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //taking input and always echoing the server response
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            while(true){
                line = in.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
