package lib.fileHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {

    public static String[] getallLinesFromFile(String path) {
        ArrayList<String> returnlines = new ArrayList<String>();
        String line;

        try {
            File file = new File(path);
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
