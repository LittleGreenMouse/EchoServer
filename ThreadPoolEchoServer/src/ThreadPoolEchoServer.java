import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolEchoServer {

    public static void main(String[] args){
        ServerSocket listenSocket = null;
        ThreadPoolExecutor executor = null;
        Socket socket = null;

        try {
            listenSocket = new ServerSocket(8189);
            executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

            System.out.println("Server listening at 8189");

            while (true) {
                socket = listenSocket.accept();
                ServerTask task = new ServerTask(socket);
                executor.execute(task);
                System.out.println("The number of clients in service is " + executor.getPoolSize());
                System.out.println("The number of clients waiting for service is:" + executor.getQueue().size());
                System.out.println("The number of clients already served is:" + executor.getCompletedTaskCount());
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(listenSocket != null)    listenSocket.close();
                if(socket != null)  socket.close();
                if(executor != null)    executor.shutdown();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
