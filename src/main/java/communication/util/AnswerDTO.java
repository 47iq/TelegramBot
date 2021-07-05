package communication.util;

import communication.keyboard.KeyboardType;
import org.jvnet.hk2.annotations.Optional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.Map;

public class AnswerDTO {
    boolean isSuccessful;
    String message;
    KeyboardType keyboardType;
    File image;
    Map<String, String> buttons;
    TelegramLongPollingBot  bot;


    public AnswerDTO(boolean isSuccessful, String message, KeyboardType keyboardType, @Optional File image, @Optional Map<String, String> buttons) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.keyboardType = keyboardType;
        this.image = image;
        this.buttons = buttons;
    }

    public TelegramLongPollingBot getBot() {
        return bot;
    }

    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public KeyboardType getKeyboard() {
        return keyboardType;
    }

    public File getImage() {
        return image;
    }

    public Map<String, String> getButtons() {
        return buttons;
    }
}
