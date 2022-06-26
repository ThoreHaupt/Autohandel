package lib.Event;

import java.util.EventListener;

/**
 * Listens to languageChangeEvent
 */
public interface LanguageChangeListener extends EventListener {
    public void languageChanged(languageChangeEvent event);
}