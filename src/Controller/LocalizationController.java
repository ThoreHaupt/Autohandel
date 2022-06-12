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
        loadLanguage(currentLanguage);
    }

    private HashMap<String, String> loadLanguage(language l) {
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

    }

    public void onLanguageChange() {

    }

}
