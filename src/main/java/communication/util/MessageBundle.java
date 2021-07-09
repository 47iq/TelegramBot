package communication.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {

    private static final String osName = System.getProperty("os.name");
    static {
        System.out.println(osName);
    }

    public static String getMessage(String key) {
        return ResourceBundle.getBundle("messages").getString(key);
    }
    public static String getSetting(String key) {
        if(osName.toLowerCase(Locale.ROOT).contains("windows"))
            return ResourceBundle.getBundle("settings_windows").getString(key);
        else
            return ResourceBundle.getBundle("settings_ubuntu").getString(key);
    }
}
