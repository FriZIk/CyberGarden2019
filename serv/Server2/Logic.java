import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Logic {
    static Properties prop = new Properties();
    public static void main(String[] args) {
        try {
            prop.load(new FileInputStream(new File(new File(".").getAbsolutePath()+"\\config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Out out = new Out();
        out.loop();
    }
}
