package communication.util;

import communication.keyboard.KeyboardType;
import game.entity.User;

/**
 * Command Data Transfer Object class
 */

public class CommandDTO {
    private final User user;
    private final String messageText;
    private String arg;
    private KeyboardType keyboardType;

    /**
     * Light constructor
     *
     * @param user        user that sent a request
     * @param messageText command
     */

    public CommandDTO(User user, String messageText) {
        this.user = user;
        this.messageText = messageText;
    }

    /**
     * Constructor
     *
     * @param user        user that sent a request
     * @param messageText command
     * @param arg         argument of a command
     */

    public CommandDTO(User user, String messageText, String arg) {
        this.user = user;
        this.messageText = messageText;
        this.arg = arg;
    }

    public KeyboardType getKeyboardType() {
        return keyboardType;
    }

    public void setKeyboardType(KeyboardType keyboardType) {
        this.keyboardType = keyboardType;
    }

    public User getUser() {
        return user;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "CommandDTO{" +
                "user=" + user +
                ", messageText='" + messageText + '\'' +
                ", arg='" + arg + '\'' +
                ", keyboardType=" + keyboardType +
                '}';
    }
}
