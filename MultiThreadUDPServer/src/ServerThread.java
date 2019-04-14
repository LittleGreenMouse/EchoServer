import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class ServerThread extends Thread {
    private DatagramSocket aSocket;
    private DatagramPacket request;

    public ServerThread(DatagramSocket aSocket, DatagramPacket request){
        this.aSocket = aSocket;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
            aSocket.send(reply);
        } catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
    }
}