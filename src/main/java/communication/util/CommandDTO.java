package communication.util;

import data.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class CommandDTO {
    private final User user;
    private final String messageText;
    private String arg;
    private TelegramLongPollingBot bot;

    public CommandDTO(User user, String messageText, TelegramLongPollingBot bot) {
        this.user = user;
        this.messageText = messageText;
        this.bot = bot;
    }

    public CommandDTO(User user, String messageText, String arg, TelegramLongPollingBot bot) {
        this.user = user;
        this.messageText = messageText;
        this.arg = arg;
        this.bot  = bot;
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

    public TelegramLongPollingBot getBot() {
        return bot;
    }
}
