package http.server;
import java.io.*;
import java.net.Socket;

public class Comm extends RequestContext {

    //reader and writer are ready
    Socket _clientSocket;
    private BufferedReader _in;
    private BufferedWriter _out;
    private int _status = 0;

    boolean http_first_line = true;
    String line;

    public Comm(Socket clientSocket)  throws IOException{
        this._clientSocket = clientSocket;
        this._in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public void set_in(BufferedReader in){
        this._in = in;
    }

    //read request
    public void readRequest() throws IOException {
        //read and save request
        System.out.println("srv: Reading request...");

        //save request
        while (_in.ready()) {
            __messageSave.append((char) _in.read());
        }

        //separate message
        String[] request = __messageSave.toString().split(System.getProperty("line.separator"));
        __messageSave = new StringBuilder();
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
                        __messageSave.append( line );
                        __messageSave.append( "\r\n" );
                    }

                }
            }
        }
        __payload = __messageSave.toString();


        sendResponse();

    }

    private void sendResponse() throws IOException {

        //check the status
        //check if the request was in order
        //if yes send the response
        //if not, the output will tell the user what the problem is
        _status = checkErrors();

        //send response back
        //determine the answer
        _out.write("HTTP/1.1 200 OK\r\n");
        _out.write("Content-Type: text/html\r\n");
        _out.write("Content-Length: 100\r\n");
        _out.write("\r\n");

        //send the answer
        sendResult(_out, _status);

        //_handler.showHeader();
        _out.flush();
        System.err.println("srv: Old client kill");
    }

    public void closeComm() throws IOException {
        _in.close();
        _out.close();
    }


}
