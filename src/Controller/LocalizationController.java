package Controller;

import java.util.HashMap;

import lib.fileHandling.FileLoader;

public class LocalizationController {
    private language currentLanguage = language.ENGLISH;
    HashMap<String, String> languageMap;
    String[] languageToString = new String[] { "en", "ger" };

    /**
     * 
     */
    public LocalizationController() {
        languageMap = new HashMap<>();
    }

    private HashMap<String, String> loadLocalization(language l) {
        languageMap = new HashMap<>();
        String lanuageCode = languageToString[l.getIndex()];
        String path = "localization/" + lanuageCode + ".txt";
        String[] localizationMap = FileLoader.getallLinesFromFile(path);
        for (int i = 0; i < localizationMap.length; i++) {
            if (localizationMap[i].equals("{")) {
                continue;
            }
            if (localizationMap[i].equals("}")) {
                continue;
            }
            char[] arr = localizationMap[i].toCharArray();
            StringBuilder key = new StringBuilder();
            int d = 1;
            while (arr[d] != (':')) {
                d++;
            }
        }
        return languageMap;
    }

    public String s(String string_inEnglish) {

        if (languageMap.containsKey(string_inEnglish)) {
            return languageMap.get(string_inEnglish);
        } else {
            addKeyToLocalizationFile(string_inEnglish);
            return string_inEnglish;
        }

    }

    private void addKeyToLocalizationFile(String key) {

    }

    public void onLanguageChange() {

    }

}
