package util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that connects Resource bundles with a project
 *
 * @see ResourceBundle
 */

public class MessageBundle {

    private static AppMode appMode = AppMode.TEST;

    private static MessageMode messageMode = MessageMode.SCH9;

    /**
     * Method that gets message from a messages resource bundle by key.
     *
     * @param key key
     * @return message
     */

    public static String getMessage(String key) {
        if(!ResourceBundle.getBundle("messages").containsKey(key))
            return ResourceBundle.getBundle("messages_" + messageMode.name().toLowerCase()).getString(key);
        else
            return ResourceBundle.getBundle("messages").getString(key);
    }

    /**
     * Method that gets message from a settings resource bundle by key depending on OS type.
     *
     * @param key key
     * @return message
     */

    public static String getSetting(String key) {
            return ResourceBundle.getBundle("settings_" + appMode.name().toLowerCase()+ "_" + messageMode.name().toLowerCase()).getString(key) ;
    }

    public static void setAppMode(AppMode appMode) {
        MessageBundle.appMode = appMode;
    }

    public static void setMessageMode(MessageMode messageMode) {
        MessageBundle.messageMode = messageMode;
    }

    public static AppMode getAppMode() {
        return appMode;
    }

    public static MessageMode getMessageMode() {
        return messageMode;
    }
}
