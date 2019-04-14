import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MultiThreadUDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        DatagramPacket request = null;
        int serverPort = 6789;

        try {
            aSocket = new DatagramSocket(serverPort);
            byte[] buffer = new byte[1000];

            int count = 0;

            while(true){
                request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                count++;
                System.out.println("The total number of clients is " + count + ".");
                ServerThread serverThread = new ServerThread(aSocket, request);
                serverThread.run();
            }
        } catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        } finally {
            if(aSocket != null) aSocket.close();
        }
    }
}
