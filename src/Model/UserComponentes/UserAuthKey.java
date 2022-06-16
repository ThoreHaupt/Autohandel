package Model.UserComponentes;

import java.io.File;
import java.io.Serializable;

import lib.fileHandling.FileSaver;

public class UserAuthKey implements Serializable {
    private String password;
    private String userProfileDataFileName;

    /**
     * @param userName
     * @param password
     * @param userProfileDataFileNameString
     */
    public UserAuthKey(String password, String userProfileDataFileNameString) {
        this.password = password;
        this.userProfileDataFileName = userProfileDataFileNameString;
    }

    public String getUserProfileDataFileName(String password) {
        if (this.password.equals(password)) {
            return userProfileDataFileName;
        }
        return "";
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void safe(String username) {
        String path = "Data/UserProfiels/UserAuthentications/" + username + ".txt";
        FileSaver.safeSerializableObject(path, this, true);
    }

}
