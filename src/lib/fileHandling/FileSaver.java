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

    public static void addToFile(String path, String input, boolean newline) {
        try {
            FileWriter myWriter = new FileWriter(path, true);
            input += newline ? "\n" : "";
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
