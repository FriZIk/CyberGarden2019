import java.io.*;
import java.util.Properties;

public class Start {

        static Start start;
        public static void main(String[] args) {
             start = new Start();
            System.out.println("Инициализация...");
            new Prop();
            new SessionManager().start();
        }


        static void exit(int code){
        System.exit(code);
    }
}