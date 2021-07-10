package communication.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that connects Resource bundles with a project
 *
 * @see ResourceBundle
 */

public class MessageBundle {

    /**
     * OS type - used for bundle switching for test and production
     */
    private static final String osName = System.getProperty("os.name");

    /**
     * Method that gets message from a messages resource bundle by key.
     *
     * @param key key
     * @return message
     */

    public static String getMessage(String key) {
        return ResourceBundle.getBundle("messages").getString(key);
    }

    /**
     * Method that gets message from a settings resource bundle by key depending on OS type.
     *
     * @param key key
     * @return message
     */

    public static String getSetting(String key) {
        if (osName.toLowerCase(Locale.ROOT).contains("windows"))
            return ResourceBundle.getBundle("settings_windows").getString(key);
        else
            return ResourceBundle.getBundle("settings_ubuntu").getString(key);
    }
}
