package LocalizationLogic;

import java.util.HashMap;

import javax.swing.event.EventListenerList;

import lib.Event.LanguageChangeListener;
import lib.Event.languageChangeEvent;
import lib.fileHandling.FileLoader;
import lib.fileHandling.FileSaver;

public class LocalizationController {
    private Language currentLanguage = Language.ENGLISH;
    HashMap<String, String> languageMap;
    String[] languageToString = new String[] { "en", "ger" };
    String[] localizationMap;

    protected EventListenerList listenerList = new EventListenerList();

    /**
     * 
     */
    public LocalizationController() {
        languageMap = new HashMap<>();
        loadLanguage(currentLanguage);
    }

    private HashMap<String, String> loadLanguage(Language l) {
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
        String storedString = "    \"" + key + "\":\"" + key + "\",";
        String[] newArray = new String[localizationMap.length + 1];
        for (int i = 0; i < localizationMap.length - 1; i++) {
            newArray[i] = localizationMap[i];
        }
        newArray[newArray.length - 2] = storedString;
        newArray[newArray.length - 1] = "}";

        String lanuageCode = languageToString[currentLanguage.getIndex()];
        FileSaver.saveFile("localization/" + lanuageCode + ".txt", newArray);

        languageMap.put(key, key);
        localizationMap = newArray;
    }

    public void fireLanguageChangeEvent(languageChangeEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == LanguageChangeListener.class) {
                ((LanguageChangeListener) listeners[i + 1]).languageChanged(event);
            }
        }
    }

    public void addLanguageChangeListener(LanguageChangeListener listener) {
        listenerList.add(LanguageChangeListener.class, listener);
    }

    public void removeLanguageChangeListener(LanguageChangeListener listener) {
        listenerList.remove(LanguageChangeListener.class, listener);
    }

    public void setLanguage(Language language) {
        currentLanguage = language;
        languageMap = new HashMap<>();
        loadLanguage(currentLanguage);
        fireLanguageChangeEvent(new languageChangeEvent(this));
    }

}
