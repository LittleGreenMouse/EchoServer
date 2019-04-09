import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadEchoServer {

    public static void main(String[] args) {
        ServerSocket listenSocket = null;
        Socket socket = null;

        try {
            listenSocket = new ServerSocket(8189);

            int count = 0;
            System.out.println("Server listening at 8189");

            while (true) {
                socket = listenSocket.accept();
                count++;
                System.out.println("The total number of clients is " + count + ".");
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(listenSocket != null)    listenSocket.close();
                if(socket != null)  socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}