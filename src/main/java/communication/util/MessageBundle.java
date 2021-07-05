package communication.util;

import java.util.ResourceBundle;

public class MessageBundle {
    public static String getMessage(String key) {
        return ResourceBundle.getBundle("messages").getString(key);
    }
}
