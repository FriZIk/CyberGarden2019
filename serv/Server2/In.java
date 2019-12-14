import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class In {
    volatile Socket socket;
    In(Socket socket) {
        this.socket = socket;

    }
    public void run() {
        byte[] b = new byte[1];
        try {
            InputStream inputStream = socket.getInputStream();
            inputStream.read(b);
            if (b[0]==(byte)1){
                System.out.println("OK");
            }
            if (b[0]==(byte)0){
                System.out.println("!OK");
            }
            Thread.sleep(100);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Соединение потерянно");
            return;
        }

    }
}
