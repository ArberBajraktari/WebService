import java.io.*;
import java.net.Socket;

public class Comm {

    //reader and writer are ready
    Socket _clientSocket;
    private final RequestContext _handler = new RequestContext();
    BufferedReader _in;
    BufferedWriter _out;
    private int _status = 0;

    public Comm(Socket clientSocket)  throws IOException{
        this._clientSocket = clientSocket;
        this._in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    //read request
    public void readRequest() throws IOException {
        System.out.println("srv: Request received");
        String line;
        boolean http_first_line = true;

        //read and save request
        while ((line = _in.readLine()) != null) {
            //break loop when done
            if (line.isEmpty()) {
                break;
            }
            if (http_first_line) {
                //initialize RequestContext type
                String[] first_line = line.split(" ");
                _handler.setMyVerb(first_line[0]);
                _handler.__message = first_line[1];
                _handler.__version = first_line[2];

                _status = _handler.checkStatus();

                http_first_line = false;
            } else {
                if(_status == 0){
                    break;
                }
                String[] other_lines = line.split(": ");
                _handler.__header.put(other_lines[0], other_lines[1]);
            }
        }

    }

    public void sendResponse() throws IOException {
        //send response back
        //determine the answer

        _out.write("HTTP/1.1 200 OK\r\n");
        _out.write("Content-Type: text/html\r\n");
        _out.write("Content-Length: 100\r\n");
        _out.write("\r\n");

        _handler.sendCommand(_out);
        //_handler.showHeader(_out);

        _out.flush();
        System.err.println("srv: Old client kill");


    }

    public void closeComm() throws IOException {
        _in.close();
        _out.close();
    }



}
