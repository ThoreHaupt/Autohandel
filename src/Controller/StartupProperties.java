package Controller;

import java.io.Serializable;
import java.util.Date;

import LocalizationLogic.Language;

public class StartupProperties implements Serializable {

    private boolean isLightTheme = false;
    private String username = "";
    private Date shutdowntime;
    private Language language = Language.ENGLISH;

    public StartupProperties() {

    }

    /**
     * @return the isLightTheme
     */
    public boolean isLightTheme() {
        return isLightTheme;
    }

    /**
     * @param isLightTheme the isLightTheme to set
     */
    public void setLightTheme(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the shutdowntime
     */
    public Date getShutdowntime() {
        return shutdowntime;
    }

    /**
     * @param shutdowntime the shutdowntime to set
     */
    public void setShutdowntime(Date shutdowntime) {
        this.shutdowntime = shutdowntime;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }
}
