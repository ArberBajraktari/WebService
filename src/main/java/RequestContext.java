import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    protected Verb __myVerb = null;
    protected String __message = null;
    protected String __version = null;
    protected int __status = 0;
    protected Map<String, String> __header = new HashMap<String, String>();


    public void setMyVerb(String myVerb) {
        if(myVerb.equals("GET")){
            __myVerb = Verb.GET;
        }else if(myVerb.equals("POST")){
            __myVerb = Verb.POST;
        }else if(myVerb.equals("PUT")){
            __myVerb = Verb.PUT;
        }else if(myVerb.equals("DELETE")){
            __myVerb = Verb.DELETE;
        }else{
            __myVerb = Verb.OTHER;
        }
    }

    public void sendCommand(BufferedWriter _out) throws IOException {
        _out.write("Type is: " + __myVerb + "\r\n");
        _out.write("Message is: " + __message + "\r\n");
        _out.write("Version is: " + __version + "\r\n");

    }

    public void showHeader(BufferedWriter _out) throws IOException{
        for (Map.Entry<String, String> entry : __header.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue() );
        }
    }

    public int checkStatus(){
        //if different command was chosen
        // 1 - command not supported
        // 2 - too many parameters
        // 3 - '/messages' not written - bad parameter

        System.out.println("srv: Checking status");

        //check if command is supported
        if(__myVerb == Verb.OTHER){
            System.out.println("srv: Request method not supported");
            return 1;
        }else{
            //split message
            String msg[] = __message.split("/");

            //check parameters
            if(msg.length > 3){
                System.out.println("srv: Too many parameters");
                return 2;
            }else{
                //check first parameter
                if(!msg[1].equals("messages")){
                    return 3;
                }else{
                    System.out.println("srv: " + __myVerb + " Request");
                    switch (__myVerb) {
                        case GET:
                            break;

                        case POST:
                            break;

                        case PUT:
                            break;

                        case DELETE:
                            break;

                    }
                }
            }

        }
        return 0;
    }

}
