package LocalizationLogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.event.EventListenerList;

import Controller.Controller;
import Controller.StartupProperties;
import lib.Event.LanguageChangeListener;
import lib.Event.languageChangeEvent;
import lib.fileHandling.FileLoader;
import lib.fileHandling.FileSaver;

/**
 * This controller controlls the language functunality. Although it is a localization Controlelr, it does not 
 * transfers prices into USD  from Euros which is the money used here
 */
public class LocalizationController {
    Controller controller;
    private Language currentLanguage = Language.ENGLISH;
    HashMap<String, String> languageMap;
    String[] languageToString = new String[] { "en", "ger" };
    String[] localizationMap;

    protected EventListenerList listenerList = new EventListenerList();

    /**
     *  constructs the localization controller
     */
    public LocalizationController(Controller c) {
        this.controller = c;
        languageMap = new HashMap<>();
        loadLanguage(currentLanguage);
        setStartUpInfoFileData();
    }

    /**
     * reads startupproperties from controller, which got it from a file storing /
     * serializable object which is saved
     * then this will set the language
     */
    private void setStartUpInfoFileData() {
        StartupProperties startupP = controller.getStartUpInfoFile();
        currentLanguage = startupP.getLanguage();
    }

    /**
     * loads the language txt files
     * @param l
     * @return
     */
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
            // mitte ??berspringen
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

    /**
     * Adds a new Key found in the programm to the language map currently selected.
     * --> I dont have to copy every String from the Programm into the file, but only replace
     * @param key
     */
    private void addKeyToLocalizationFile(String key) {
        /* char[] charArr = key.toCharArray();
        ArrayList<Character> charList = new ArrayList<>(); */

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

    /**
     * Fires when the language changes, causing all texts in the programm to reload
     * @param event the event instance
     */
    public void fireLanguageChangeEvent(languageChangeEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == LanguageChangeListener.class) {
                ((LanguageChangeListener) listeners[i + 1]).languageChanged(event);
            }
        }
    }

    /**
     * Add LanguageChangeListener
     * @param listener the Listener
     */
    public void addLanguageChangeListener(LanguageChangeListener listener) {
        listenerList.add(LanguageChangeListener.class, listener);
    }

    /**
     * remove LanguageChangeListener
     * @param listener the Listener
     */
    public void removeLanguageChangeListener(LanguageChangeListener listener) {
        listenerList.remove(LanguageChangeListener.class, listener);
    }

    /**
     * Changes the language to input and then calls the Event thus notifying all Labels/TextFields... to reset their text
     * @param language Language to be changed to
     */
    public void setLanguage(Language language) {
        currentLanguage = language;
        languageMap = new HashMap<>();
        loadLanguage(currentLanguage);
        fireLanguageChangeEvent(new languageChangeEvent(this));
    }

    /**
     * returns an array with all the Languages as useable Strings from the Language enum
     * @return
     */
    public static String[] getLanguageStringArray() {
        return (String[]) Arrays.asList(Language.values()).stream().map(e -> e.toString())
                .collect(Collectors.toList()).toArray(new String[Language.values().length]);
    }

    public void resetLanguage() {
        setLanguage(currentLanguage);
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

}
