import http.server.Comm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebService {

    static ServerSocket _sSocket = null;
    static int _port;
    static Comm comm;
    static List<String> __messagesSaved = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("srv: Starting server...");


        //start the server
        try{
            _port = Integer.parseInt(args[0]);
        }catch(Exception e){
            System.out.println("srv: Please enter a valid port!");
            return;
        }

        _sSocket = new ServerSocket(_port);
        System.err.println("srv: Server is running in port " + _port);

        // repeatedly wait for connections, and process
        // noinspection InfiniteLoopStatement
        while (true) {
            // connect to client
            Socket clientSocket = _sSocket.accept();
            System.out.println("srv: New client");

            //initialize communication
            comm = new Comm(clientSocket, __messagesSaved);

            //read command
            //send reply to command
            comm.readRequest();

            //close communication
            comm.closeComm();


            //close the client socket
            clientSocket.close();

        }
    }
}
