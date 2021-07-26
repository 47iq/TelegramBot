package communication.util;

import data.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

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

    public CommandDTO(User user, String messageText, TelegramLongPollingBot bot) {
        this.user = user;
        this.messageText = messageText;
    }

    /**
     * Constructor
     *
     * @param user        user that sent a request
     * @param messageText command
     * @param bot         bot(needed for async operations)
     * @param arg         argument of a command
     */

    public CommandDTO(User user, String messageText, String arg, TelegramLongPollingBot bot) {
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
