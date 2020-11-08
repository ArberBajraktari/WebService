import java.io.*;
import java.net.Socket;

public class Comm {

    //reader and writer are ready
    private Socket _clientSocket = null;
    private RequestContext _handler = new RequestContext();
    private BufferedReader _in = null;
    private BufferedWriter _out = null;

    public Comm(Socket clientSocket)  throws IOException{
        this._clientSocket = clientSocket;
        this._in = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
        this._out = new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));
    }

    //read request
    public void readRequest() throws IOException {
        String line;
        boolean http_first_line = true;

        //read and save request
        while ((line = _in.readLine()) != null) {
            //break loop when done
            if (line.isEmpty()) {
                break;
            }
            if (http_first_line) {

                http_first_line = false;
            } else {

            }
            System.out.println(line);
        }
        System.out.println("srv: Request received");

        _out.write("HTTP/1.1 200 OK\r\n");
        _out.write("Content-Type: text/html\r\n");
        _out.write("Content-Length: 100\r\n");
        _out.write("\r\n");
        _out.write("<TITLE>Example</TITLE>\r\n");
        System.err.println("srv: Old client kill");
        /*if(_handler.getMyVerb() == Verb.GET){
            _out.write("<p> get </p>");
        }else if(_handler.getMyVerb() == Verb.POST){
            _out.write("<p>POST </p>");
        }else{
            _out.write("<p>Other</p>");
        }*/
    }

    public void sendResponse() throws IOException {



    }

    public void closeComm() throws IOException {
        _in.close();
        _out.close();
    }



}
