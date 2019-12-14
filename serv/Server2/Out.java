import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Out {
    String IP = Logic.prop.getProperty("IP");
    int PORT = Integer.parseInt(Logic.prop.getProperty("PORT"));
    Socket socket;
    boolean lock = false;
    void loop(){
        File path = new File(new File(".").getAbsolutePath()+"\\dir");
        while (true){
            if (path.listFiles().length!=0&&!lock){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File[] files = path.listFiles();
                lock = true;
                out(files[0]);
            }
        }

    }

    void out(File file){

        String fileName = file.getName();
        try {
            DataOutputStream outF = new DataOutputStream(socket.getOutputStream());
            FileInputStream inF = new FileInputStream(file);
            byte[] bytes = new byte[5 * 1024];
            int count;
            long lenght = file.length();
            outF.writeLong(lenght);
            while ((count = inF.read(bytes)) > -1) {
                outF.write(bytes, 0, count);
            }
            in(socket);
            inF.close();
            outF.close();
            System.out.println("Файл успешно передан");
            delete(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Передаваемый файл не найден");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Соединение потерянно");
            return;
        } finally {
            lock = false;

        }
    }
    Date time;
    volatile boolean interrupted;
    void in(Socket s){
        interrupted = false;
        In answer = new In(s);
        answer.run();
    }
    void delete(File file){
        System.out.println("Файл удален - "+ file.getName() +" "+ file.delete());
    }
}
