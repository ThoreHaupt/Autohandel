package lib.Event;

import java.util.EventListener;

public interface LanguageChangeListener extends EventListener {
    public void languageChanged(languageChangeEvent event);
}