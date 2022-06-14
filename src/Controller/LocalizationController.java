package Controller;

import java.util.HashMap;

import lib.fileHandling.FileLoader;
import lib.fileHandling.FileSaver;

public class LocalizationController {
    private language currentLanguage = language.GERMAN;
    HashMap<String, String> languageMap;
    String[] languageToString = new String[] { "en", "ger" };
    String[] localizationMap;

    /**
     * 
     */
    public LocalizationController() {
        languageMap = new HashMap<>();
        loadLanguage(currentLanguage);
    }

    private HashMap<String, String> loadLanguage(language l) {
        languageMap = new HashMap<>();
        String lanuageCode = languageToString[l.getIndex()];
        String path = "localization/" + lanuageCode + ".txt";
        localizationMap = FileLoader.getallLinesFromFile(path);
        for (int i = 0; i < localizationMap.length; i++) {
            if (localizationMap[i].equals("{")) {
                continue;
            }
            if (localizationMap[i].equals("}")) {
                continue;
            }
            char[] arr = localizationMap[i].toCharArray();
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();

            int d = 0;
            // white space
            while (arr[d] != ('"')) {
                d++;
            }
            d++;
            while (arr[d] != ('"')) {

                if (arr[d] == '\\') {
                    key.append(arr[d + 1]);
                    d += 2;
                    continue;
                }
                key.append(arr[d]);
                d++;
            }
            d++;
            // mitte Überspringen
            while (arr[d] != ('"')) {
                d++;
            }
            d++;
            while (arr[d] != ('"')) {
                if (arr[d] == '\\') {
                    value.append(arr[d + 1]);
                    d += 2;
                    continue;
                }
                value.append(arr[d]);
                d++;
            }
            languageMap.put(key.toString(), value.toString());
        }
        return languageMap;
    }

    /**
     * returns the string in the set Lanugage
     * 
     * @param string_inEnglish Key
     * @return translated Key
     */
    public String s(String string_inEnglish) {

        if (languageMap.containsKey(string_inEnglish)) {
            return languageMap.get(string_inEnglish);
        } else {
            addKeyToLocalizationFile(string_inEnglish);
            return string_inEnglish;
        }

    }

    private void addKeyToLocalizationFile(String key) {
        String storedString = "    \"" + key + "\":\"\",";
        String[] newArray = new String[localizationMap.length + 1];
        for (int i = 0; i < localizationMap.length - 1; i++) {
            newArray[i] = localizationMap[i];
        }
        newArray[newArray.length - 2] = storedString;
        newArray[newArray.length - 1] = "}";

        String lanuageCode = languageToString[currentLanguage.getIndex()];
        FileSaver.saveFile("localization/" + lanuageCode + ".txt", newArray);

        languageMap.put(key, key);
    }

    public void onLanguageChange(language newl) {

    }

}
