import java.io.*;
import java.net.Socket;

public class Test {
    static Socket socket = null;

    public static void main(String[] args) throws IOException, InterruptedException {
        socket = new Socket("localhost",3000);
        File f = new File("./dir/img.jpg");
        System.out.println(f.exists());
        out(f);
    }

    static void out(File file) {
        try {
            DataOutputStream outF = new DataOutputStream(socket.getOutputStream());
            FileInputStream inF = new FileInputStream(file);
            byte[] bytes = new byte[5 * 1024];
            int count;
            socket.getOutputStream().write(200);
            long lenght = file.length();
            outF.writeLong(lenght);
            while ((count = inF.read(bytes)) > -1) {
                outF.write(bytes, 0, count);
            }
            inF.close();
            while (socket.getInputStream().read()!=1){}
            System.out.println("Файл успешно передан");
            outF.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Передаваемый файл не найден");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Соединение потерянно");
            return;
        }
    }

//
//            System.out.println("answer - " + dis.readUTF());
}



