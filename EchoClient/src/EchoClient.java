import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {

        InputStream inStream = null;
        BufferedReader in = null;
        OutputStream outStream = null;
        PrintWriter out = null;
        Socket socket  = null;

        BufferedReader stdIn = null;
        String userInput;
        String echoMessage;

        try {
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            socket = new Socket("127.0.0.1", 8189);
            System.out.println("Connected to Server");

            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inStream));
            out = new PrintWriter(outStream);

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                out.flush();
                echoMessage = in.readLine();
                System.out.println("Echo from server: " + echoMessage);
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(out != null) out.close();
                if(outStream != null)   outStream.close();
                if(in != null)  in.close();
                if(inStream != null)    inStream.close();
                if(stdIn != null)   stdIn.close();
                if(socket != null)  socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}