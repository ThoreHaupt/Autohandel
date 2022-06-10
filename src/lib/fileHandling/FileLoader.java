package lib.fileHandling;

import java.io.File;
import java.io.FileNotFoundException;
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

        String[] returnlinesArray = (String[]) returnlines.toArray();
        return returnlinesArray;
    }
}
