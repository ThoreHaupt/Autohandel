package Model.UserComponentes;

import java.io.Serializable;

import lib.fileHandling.FileSaver;

public class UserAuthKey implements Serializable {
    public final static String authKeyFolderPath = "Data/UserProfiles/UserAuthentications/";
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
        String path = authKeyFolderPath + username + ".ser";
        FileSaver.safeSerializableObject(path, this, true);
    }

}
