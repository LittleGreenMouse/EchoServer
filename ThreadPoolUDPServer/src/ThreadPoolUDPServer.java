import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUDPServer {

    public static void main(String[] args){
        DatagramSocket aSocket = null;
        DatagramPacket request = null;
        int serverPort = 6789;
        ThreadPoolExecutor executor;

        try {
            aSocket = new DatagramSocket(serverPort);
            executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
            byte[] buffer = new byte[1000];

            System.out.println("Server listening at 6789");

            while (true) {
                request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                ServerTask task = new ServerTask(aSocket, request);
                executor.execute(task);
                System.out.println("The number of clients in service is " + executor.getPoolSize());
                System.out.println("The number of clients waiting for service is:" + executor.getQueue().size());
                System.out.println("The number of clients already served is:" + executor.getCompletedTaskCount());
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
