import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTask implements Runnable{

    private Socket socket;

    public ServerTask(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inStream = null;
        BufferedReader in = null;
        OutputStream outStream = null;
        PrintWriter out = null;

        try{
            inStream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inStream));
            outStream = socket.getOutputStream();
            out = new PrintWriter(outStream);
            String line;
            while((line=in.readLine()) != null){
                System.out.println("Message from client:" + line);
                out.println(line);
                out.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) out.close();
                if(outStream != null)   outStream.close();
                if(in != null)  in.close();
                if(inStream != null)    inStream.close();
                if(socket != null)  socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
