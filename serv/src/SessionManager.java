import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager extends Thread{
    static final SessionManager inst = new SessionManager();

    public static SessionManager getInstance() {
        return inst;
    }
    int count = 0;
   public static volatile HashMap<Integer, Session> sessions = new HashMap<>();
    synchronized int getSize(){
        return sessions.size();
    }
    void addInTheMap(Session s){
        int i = sessions.size();
        if (i < Integer.parseInt(Prop.getI().getPropertie("MAX_USERS_COUNT"))) {
            sessions.put(count, s);
            System.out.println("Новое подключение - "+count+++" "+i+"/"+Prop.getI().getPropertie("MAX_USERS_COUNT"));
        }
        else{
            System.out.println("Новое подключение - превышен лимит пользователей - отключение");
            disconnect(s);
        }
    }
    synchronized void removeFromMap(Session s){
        sessions.remove(s.id);
        System.out.println("Сессия "+s.id+" удалена из списка "+getSize()+"/"+Prop.getI().getPropertie("MAX_USERS_COUNT"));
    }
    @Override
    public void run(){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(Prop.getI().getPropertie("PORT")));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Не удалось запустить Session Manager");
            Start.exit(-1);
        }
        System.out.println("Input класс закружился~~");
        while (true){
            try {
                assert serverSocket!=null;
                Session s = new Session(serverSocket.accept(),count);
                addInTheMap(s);
                s.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Session extends Thread{
        volatile boolean isHello = false;
        volatile int id;
        volatile Socket socket;
        Session(Socket socket, int id){
            this.id = id;
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                new Timer(this,10000).start();
                isHello = SessionManager.hello(is,os);
                //todo test
                System.out.println("hello - ok");
                dialog();
            } catch (IOException e) {
                e.printStackTrace();
                disconnect(this);
            }
        }
        public Socket getSocket() {
            return socket;
        }

        void dialog(){
            try {
                FileOutputStream out;
                byte[] bytes = new byte[100*1024];
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                String info = dis.readUTF();
                out = new FileOutputStream(new File("./images/img"+id+".jpg"));
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int count, total = 0;
                long lenght = in.readLong();
                while ((count = socket.getInputStream().read(bytes)) > -1) {
                    total += count;
                    out.write(bytes, 0, count);
                    if (total == lenght) break;
                }
                out.close();
                System.out.println("Файл успешно получен");
                socket.getOutputStream().write(1);
            } catch (IOException e) {
                e.printStackTrace();
                disconnect(this);
            }
        }
    }
    static boolean hello(InputStream is, OutputStream os) throws IOException {
        byte[] b = new byte[1];
        os.write((byte)200);
        is.read(b);
        if (b[0]==200) return true;
        return false;
    }
    static class Timer extends Thread{
        Session t;
        int mill;
        Timer(Session t, int mill){
            this.t = t;
            this.mill = mill;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(mill);
                if(!t.isHello) {
                    disconnect(t);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static void disconnect(Session t){
        getInstance().removeFromMap(t);
        try {
            t.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        t.interrupt();
        t.stop();
    }

}
