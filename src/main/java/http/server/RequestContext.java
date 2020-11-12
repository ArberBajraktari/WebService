package http.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract class RequestContext {

    protected Verb __myVerb = Verb.OTHER;
    protected String __message;
    protected String __version;
    protected String __payload;
    protected StringBuilder __messageSave = new StringBuilder();
    protected Map<String, String> __header = new HashMap<>();

    public Verb get__myVerb() {
        return __myVerb;
    }

    protected void setMyVerb(String myVerb) {
        switch (myVerb) {
            case "GET":
                __myVerb = Verb.GET;
                break;
            case "POST":
                __myVerb = Verb.POST;
                break;
            case "PUT":
                __myVerb = Verb.PUT;
                break;
            case "DELETE":
                __myVerb = Verb.DELETE;
                break;
            default:
                __myVerb = Verb.OTHER;
                break;
        }
    }

    protected void sendResult(BufferedWriter _out, int status) throws IOException {
        switch (status){
            //all worked okay
            case 0:
                System.out.println("srv: " + __myVerb + " request is being processed");
                switch (__myVerb){
                    case GET:
                        _out.write("get");
                        get();
//                        System.out.println("get");
                        break;
                    case POST:
                        _out.write("post");
                        post();
//                        System.out.println("post");
                        break;
                    case PUT:
                        _out.write("put");
                        put();
//                        System.out.println("put");
                        break;
                    case DELETE:
                        _out.write("delete");
                        delete();
//                        System.out.println("srv: Delete");
                        break;
                    default:
                        _out.write("Error has occurred\r\n");
                        System.out.println("srv: Error has occurred");
                }

                break;
            //problems...
            case 1:
                _out.write("err: Command is not supported\r\n");
                break;
            case 2:
                _out.write("err: Too many parameters in the request\r\n");
                break;
            case 3:
                _out.write("err: Bad parameter\r\n");
                _out.write("err: You have either an extra spacebar or bad input\r\n");
                break;
            case 4:
                _out.write("err: Few parameters\r\n");
                break;
            case 5:
                _out.write("err: POST accepts only 1 parameter\r\n");
                break;
            case 6:
                _out.write("err: DELETE needs 2 parameters\r\n");
                _out.write("Write which message to delete\r\n");
                break;
            case 7:
                _out.write("err: PUT needs 2 parameters\r\n");
                break;
            case 8:
                _out.write("err: POST has no text to save\r\n");
                break;
            case 9:
                _out.write("err: PUT has not text to update\r\n");
                break;

        }
    }

    public void showHeader(){
        System.out.println("header:");
        for (Map.Entry<String, String> entry : __header.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue() );
        }
    }

    protected int checkErrors(){
        //if different command was chosen
        // 1 - command not supported
        // 2 - too many parameters
        // 3 - '/messages' not written - bad parameter
        // 4 - few parameters
        // 5 - POST accepts only 1 parameter
        // 6 - DELETE few parameters
        // 7 - PUT few parameters
        // 8 - POST has no text to save
        // 10 - extra text is written
        System.out.println("srv: Checking status");

        //check if command is supported
        if(__myVerb == Verb.OTHER){
            System.out.println("srv: Request method not supported");
            return 1;
        }else{
            //split message
            String[] msg = __message.split("/");

            //check parameters
            if(msg.length > 3){
                System.out.println("srv: Too many parameters");
                return 2;
            }else if(msg.length < 2){
                System.out.println("srv: Too few parameters");
                return 4;
            }else{
                if( msg.length == 3 && msg[2].contains("%20")){
                    System.out.println("srv: Extra text is written in the request");
                    return 3;
                }
//
                //check first parameter
                if(!msg[1].equals("messages")){
                    System.out.println("srv: Command file not known");
                    return 3;
                }else{
                    switch (__myVerb) {
                        case POST:
                            if(msg.length == 3){
                                System.out.println("srv: POST accepts only 1 parameter");
                                return 5;
                            }
                            break;

                        case DELETE:
                            if(msg.length == 2){
                                System.out.println("srv: DELETE has few parameters");
                                return 6;
                            }
                            break;

                        case PUT:
                            if(msg.length == 2){
                                System.out.println("srv: PUT has few parameters");
                                return 7;
                            }
                            break;
                    }
                }
            }

        }
        return 0;
    }

    //to be written
    protected void get(){

    }

    protected void post(){

    }

    protected void put(){

    }

    protected void delete(){

    }

}
