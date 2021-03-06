package lib.fileHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {

    /**
     * returns an Array of all String lines in a file path
     * @param file
     * @return
     */
    public static String[] getallLinesFromFile(String path) {
        return getallLinesFromFile(new File(path));
    }

    /**
     * returns an Array of all String lines in a file
     * @param file
     * @return
     */
    public static String[] getallLinesFromFile(File file) {
        ArrayList<String> returnlines = new ArrayList<String>();
        String line;

        try {
            Scanner filereader = new Scanner(file);
            while (filereader.hasNextLine()) {
                line = filereader.nextLine();
                if (!line.equals(""))
                    returnlines.add(line);
            }
            filereader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String[] returnlinesArray = new String[returnlines.size()];
        for (int i = 0; i < returnlinesArray.length; i++) {
            returnlinesArray[i] = returnlines.get(i);
        }
        return returnlinesArray;
    }

    /**
     * returns a serialized Object by its filePath
     */
    public static Object loadSerializedObject(File file) throws ClassNotFoundException {

        try {
            FileInputStream datIn = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(datIn);
            Object object = oi.readObject();
            oi.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
