package Controller;

import java.util.HashMap;

public class LocalizationController {
    private language currentLanguage = language.ENGLISH;
    HashMap<String, String> languageMap;

    /**
     * 
     */
    public LocalizationController() {
        languageMap = new HashMap<>();
    }

    public HashMap<String, String> loadLocalization(language l) {
        return new HashMap<String, String>();
    }

    public String s(String string_inEnglish) {
        return string_inEnglish;
    }

}
