package lib.fileHandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    public static void saveFile(String path, String[] arr) {
        try {
            FileWriter fwriter = new FileWriter(new File(path));
            for (String string : arr) {
                fwriter.append(string + "\n");
            }
            fwriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
