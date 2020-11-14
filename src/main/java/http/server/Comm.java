package http.server;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Comm extends RequestContext {

    //reader and writer are ready
    Socket _clientSocket;
    private BufferedReader _in;
    private final BufferedWriter _out;
    boolean http_first_line = true;

    public Comm(Socket clientSocket, List<String> __messagesSaved)  throws IOException{
        this._clientSocket = clientSocket;
        this._in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.__messagesSaved = __messagesSaved;
    }

    public void set_in(BufferedReader in){
        this._in = in;
    }

    private void readMessage() throws IOException {
        //read and save request
        System.out.println("srv: Reading request...");

        //save request
        while (_in.ready()) {
            __messageSeparator.append((char) _in.read());
        }
    }


    private void separateMessage(){
        //separate message
        String[] request = __messageSeparator.toString().split(System.getProperty("line.separator"));
        __messageSeparator = new StringBuilder();
        boolean skip = true;
        for (String line: request){
            if(!line.isEmpty()){
                if (http_first_line) {
                    //saving folder and version
                    String[] first_line = line.split(" ");
                    setMyVerb(first_line[0]);
                    __message = first_line[1];
                    __version = first_line[2];
                    http_first_line = false;
                } else {

                    //saving the header
                    if(line.contains(": ")){
                        String[] other_lines = line.split(": ");
                        __header.put(other_lines[0], other_lines[1]);
                    }
                    //saving the payload
                    else{
                        if (skip){
                            skip = false;
                        }else{
                            __messageSeparator.append( line );
                            __messageSeparator.append( "\r\n" );
                        }

                    }

                }
            }
        }
        __payload = __messageSeparator.toString();
    }

    //read request
    public void readRequest() throws IOException {

        readMessage();
        separateMessage();
        sendResponse();

    }

    private void sendResponse() throws IOException {

        //check the status
        //check if the request was in order
        //if yes send the response
        //if not, the output will tell the user what the problem is
        int _status = checkErrors();

        //send response back
        //determine the answer
        _out.write("HTTP/1.1 200 OK\r\n");
        _out.write("Content-Type: text/html\r\n");
        _out.write("Content-Length: 100\r\n");
        _out.write("\r\n");

        //send the answer
        sendResult(_out, _status);
        _out.flush();

        System.err.println("srv: Old client kill");
    }

    public void closeComm() throws IOException {
        _in.close();
        _out.close();
    }


}
