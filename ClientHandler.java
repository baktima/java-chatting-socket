package Chatting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final List<PrintWriter> clientWriters;

    public ClientHandler(Socket client, List<PrintWriter> clientWriters) {
        this.clientSocket = client;
        this.clientWriters = clientWriters;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //write out
            out = new PrintWriter(clientSocket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String statement = "";

            while (!statement.equals("tomato")) {
                //write the received message to the server
                statement = in.readLine();

                //printing the message to the server side
                System.out.printf("sent from client %s: %s \n",Thread.currentThread().getId(), statement);

                //renew the statement and adding the unique thread to echo to all of the thread
                statement = "This message was sent from client " + Thread.currentThread().getId()+ " :"+statement;

                //this part echoed all the statement to the other Thread
                synchronized (clientWriters){
                    for(PrintWriter writer: clientWriters){
                        writer.println(statement);
                        writer.flush();
                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
