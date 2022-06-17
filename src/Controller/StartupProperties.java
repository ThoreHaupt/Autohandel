package Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import LocalizationLogic.Language;

public class StartupProperties implements Serializable {

    private transient Controller c;
    private boolean isLightTheme = false;
    private String username = "";
    private Date shutdowntime;
    private Language language;

    public StartupProperties(Controller c) {

    }

    public void setController(Controller c) {
        this.c = c;
    }

    /* private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        shutdowntime = new Date();
    
        aOutputStream.writeBoolean(isLightTheme);
        aOutputStream.writeUTF(username);
        aOutputStream.writeLong(shutdowntime.getTime());
    } */

}
