import http.server.Comm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestComm {

    ServerSocket mockServerSocket;
    Socket mockTestClientSocket;
    Comm com;

    @Before
    public void setup() {
        //mock Socket and ServerSocket classes
        mockServerSocket = mock(ServerSocket.class);
        mockTestClientSocket = mock(Socket.class);

        try {
            //prevent Unnecessary Stubbing exception with lenient()
            lenient().when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        mockTestClientSocket = mock(Socket.class);
        try {
            PipedOutputStream oStream = new PipedOutputStream();
            lenient().when(mockTestClientSocket.getOutputStream()).thenReturn(oStream);

            PipedInputStream iStream = new PipedInputStream(oStream);
            lenient().when(mockTestClientSocket.getInputStream()).thenReturn(iStream);

            lenient().when(mockTestClientSocket.isClosed()).thenReturn(false);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            com = new Comm(mockTestClientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // return an InputStream with a dummy request
    public InputStream getInputStream() {
        return new ByteArrayInputStream("GET / HTTP/1.1\nHost: localhost".getBytes());
    }

    @Test
    public void test() {
        try {
            BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
            when(bufferedReader.readLine()).thenReturn("first line").thenReturn("second line");
            com.set_in(bufferedReader);
            com.readRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void testReadRequest() throws IOException {
//
//        ServerSocket mockServerSocket = mock(ServerSocket.class);
//        Socket mockTestClientSocket = mock(Socket.class);
//
//        try {
//            // Then mock it
//            when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
//        } catch (IOException e) {
//            fail(e.getMessage());
//        }
//
//
//    }
}
