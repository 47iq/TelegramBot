package util;

import model.User;
import org.springframework.stereotype.Component;

@Component
public class CommandDTO {
    private final User user;
    private final String messageText;
    private String arg;

    public CommandDTO(User user, String messageText) {
        this.user = user;
        this.messageText = messageText;
    }

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
}
