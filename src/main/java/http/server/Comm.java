package http.server;

import java.io.*;
import java.net.Socket;

public class Comm extends RequestContext {

    //reader and writer are ready
    Socket _clientSocket;
    private final BufferedReader _in;
    private final BufferedWriter _out;
    private int _status = 0;

    boolean http_first_line = true;
    String line;

    public Comm(Socket clientSocket)  throws IOException{
        this._clientSocket = clientSocket;
        this._in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    //read request
    public void readRequest() throws IOException {
        System.out.println("srv: Request received");

        //read and save request
        while ((line = _in.readLine()) != null) {
            //break loop when done
            if (line.isEmpty()) {
                break;
            }

            System.out.println("srv: Reading header...");
            //save request header
            if (http_first_line) {
                //initialize http.server.RequestContext type
                String[] first_line = line.split(" ");
                setMyVerb(first_line[0]);
                __message = first_line[1];
                __version = first_line[2];

                http_first_line = false;
            } else {
                //if errors do not continue
                if(_status != 0){
                    break;
                }
                String[] other_lines = line.split(": ");
                __header.put(other_lines[0], other_lines[1]);
            }

            System.out.println("srv: Reading payload...");
            while(_in.ready()){
                __messageSave.append((char) _in.read());
            }
            __payload = __messageSave.substring(__messageSave.lastIndexOf("\r\n\r\n"));
            //System.out.println("srv: Payload is: " + __payload);
            //check for errors
            _status = checkStatus();
            break;
        }

        //return response
        sendResponse();

    }

    private void sendResponse() throws IOException {
        //send response back
        //determine the answer
        _out.write("HTTP/1.1 200 OK\r\n");
        _out.write("Content-Type: text/html\r\n");
        _out.write("Content-Length: 100\r\n");
        _out.write("\r\n");

        sendCommand(_out, _status);

        //_handler.showHeader();
        _out.flush();
        System.err.println("srv: Old client kill");


    }

    public void closeComm() throws IOException {
        _in.close();
        _out.close();
    }



}
