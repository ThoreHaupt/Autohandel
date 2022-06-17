package lib.fileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileSaver {

    /**
     * This creates a new file and writes the given String Array into each line 
     * Overrides old file on default
     * @param path filepath
     * @param arr String Array with the to be written Content
     */
    public static void saveFile(String path, String[] arr) {
        saveFile(path, arr, true);
    }

    /**
     * This creates a new file and writes the given String Array into each line
     * @param path filepath
     * @param arr String Array with the to be written Content
     */
    public static void saveFile(String path, String[] arr, boolean override) {
        File file = new File(path);
        if (file.exists() && override) {
            file.delete();
            file = new File(path);
        }
        try {
            FileWriter fwriter = new FileWriter(new File(path));
            for (String string : arr) {
                fwriter.append(string + "\n");
            }
            fwriter.flush();
        } catch (IOException e) {
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

    /**
     * Writes a serializable object to a given File. 
     * @param path Path of Object with name of the file
     * @param o the serializable Object
     * @param OverrideWhenHaveTo if{@code} true} this will override the old file if it is still there
     */
    public static void safeSerializableObject(String path, Serializable o, boolean OverrideWhenHaveTo) {
        int number = 0;
        File file = new File(path);
        if (OverrideWhenHaveTo) {
            while (file.exists()) {
                file = new File(path + " (" + number + ")");
                number++;
            }
        } else {
            file.delete();
            file = new File(path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream datAus;
        try {
            datAus = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(datAus);
            outputStream.writeObject(o);
            outputStream.close();
        } catch (FileNotFoundException e) {
            // this wont happen, because the file gets created above
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
