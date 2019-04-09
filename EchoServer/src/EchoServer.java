import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {

        InputStream inStream = null;
        OutputStream outStream = null;
        BufferedReader in = null;
        PrintWriter out = null;
        ServerSocket listenSocket = null;
        Socket clientSocket = null;

        try {
            listenSocket = new ServerSocket(8189);

            System.out.println("Server listening at 8189");
            clientSocket = listenSocket.accept();
            System.out.println("Accepted connection from client");

            inStream = clientSocket.getInputStream();
            outStream = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inStream));
            out = new PrintWriter(outStream);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Message from client:" + line);
                out.println(line);
                out.flush();
            }
            clientSocket.close();
            listenSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(out != null) out.close();
                if(outStream != null)   outStream.close();
                if(in != null)  in.close();
                if(inStream != null)    inStream.close();
                if(listenSocket != null)    listenSocket.close();
                if(clientSocket != null)    clientSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}