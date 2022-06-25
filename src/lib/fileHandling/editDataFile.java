package lib.fileHandling;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This is just to do edeting of the dataset, because I do not want to make 200+ manual adjustments
 */
public class editDataFile {
    /* public static void main(String[] args) {
        changeData();
    } */

    public static void changeData() {
        File f = new File("Data/commons/ElectricCarSpreadSheet.csv");
        String[] dataArray = FileLoader.getallLinesFromFile(f);
        String[] splitededDataHeaders = dataArray[0].split(";");
        String[] newArr = dataArray.clone();

        for (int i = 5; i < dataArray.length; i++) {
            String[] splintedLine = dataArray[i].split(";");
            if (splintedLine.length > 0)
                splintedLine[1] = splintedLine[1].substring(5, splintedLine[1].length());

            newArr[i] = Arrays.asList(splintedLine).stream().map(Object::toString).collect(Collectors.joining(";"));
            newArr[i] += ";" + splintedLine[0].split(" ")[0];
        }

        FileSaver.saveFile(f, newArr);

    }
}
