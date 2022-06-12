package Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class StartupProperties implements Serializable {

    private boolean isLightTheme = false;
    private String username = "Guest";
    private Date shutdowntime;

    public StartupProperties() {

    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        shutdowntime = new Date();

        aOutputStream.writeBoolean(isLightTheme);
        aOutputStream.writeUTF(username);
        aOutputStream.writeLong(shutdowntime.getTime());
    }

}
