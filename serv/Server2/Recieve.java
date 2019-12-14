package Server2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Properties;

public class Recieve extends Thread {

    @Override
    public void run() {


        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("./config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerSocket serversocket = null;
        try {
            serversocket = new ServerSocket(Integer.parseInt(properties.getProperty("PORT")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        Socket socket = null;
        while (!serversocket.isClosed()) {
            try {
                socket = serversocket.accept();
                System.out.println("new connection");
//                ObjectInputStream obIn = new ObjectInputStream(socket.getInputStream());

                out = new FileOutputStream(new File("./images/img.jpg"));
                DataInputStream in = new DataInputStream(socket.getInputStream());
                    byte[] bytes = new byte[100 * 1024];
                    int count, total = 0;
                    long lenght = in.readLong();
                    while ((count = in.read(bytes)) > -1) {
                        total += count;
                        out.write(bytes, 0, count);
                        if (total == lenght) break;
                    }
                    out.close();
                System.out.println("Файл успешно получен");

                testOut(socket);
            } catch (IOException ignored) {
                ignored.printStackTrace();
                if (out!=null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    void testOut(Socket s){
        try {
            OutputStream stream = s.getOutputStream();
            if(listenTxtFile()) {
                stream.write((byte) 1);
            } else {
                stream.write((byte) 0);
            }
            stream.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean listenTxtFile(){
            File f = new File(new File(".").getAbsolutePath()+"\\rezult\\");

            while (f.listFiles().length==0){
            }

        File[] rez = f.listFiles();
            if (rez[0].getName().equals("1.r")){
                rez[0].delete();

                return true;
            } else {
                rez[0].delete();
                return false;
            }

    }
}
