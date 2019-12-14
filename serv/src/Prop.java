import java.io.*;
import java.util.Properties;

public class Prop {
    static private Prop instance;
    private Properties prop = new Properties();

    Prop() {
        File propFile = new File("./config");
        if (!propFile.exists()) {
            try {
                propFile.createNewFile();
                new FileOutputStream(propFile).write(STANDART_CONFIG.getBytes());
                prop.load(new FileReader(propFile));
                System.out.println("Файл конфига создан.");
                instance = this;
                System.out.println("Конфиг - OK");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Не удалось создать файл конфига.");
            }
        } else {
            try {
                prop.load(new FileReader(propFile));
                instance = this;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Не удалось считать файл конфига");

            }

        }
    }

    static Prop getI() {
        return instance;
    }


    private final String STANDART_CONFIG = "" +
            "PORT = 3000\n" +
            "MAX_USERS_COUNT = 5\n" +
            "INPUT_BUFFER_SIZE = 8192\n" +
            "";

    String getPropertie(String str) {
        return prop.getProperty(str);
    }
}

