package communication.util;

import game.entity.User;

/**
 * Command Data Transfer Object class
 */

public class CommandDTO {
    private final User user;
    private final String messageText;
    private String arg;

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
}
