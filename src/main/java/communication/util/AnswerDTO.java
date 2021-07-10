package communication.util;

import communication.keyboard.KeyboardType;
import game.entity.CardName;
import org.jvnet.hk2.annotations.Optional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.Map;

/**
 * Answer Data Transfer Object class
 */

public class AnswerDTO {
    boolean isSuccessful;
    String message;
    KeyboardType keyboardType;
    File image;
    Map<String, String> buttons;
    TelegramLongPollingBot bot;
    CardName cardName;

    /**
     * Constructor
     *
     * @param isSuccessful is the answer successful
     * @param message      answer text
     * @param keyboardType keyboard type for a Telegram messenger
     * @param image        image that needs to be sent as well
     * @param buttons      buttons for a custom keyboard(if needed)
     */

    public AnswerDTO(boolean isSuccessful, String message, KeyboardType keyboardType, @Optional File image, @Optional Map<String, String> buttons) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.keyboardType = keyboardType;
        this.image = image;
        this.buttons = buttons;
    }

    public CardName getCardName() {
        return cardName;
    }

    public void setCardName(CardName cardName) {
        this.cardName = cardName;
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

    /**
     * Method that appends message to the end skipping two lines of text
     *
     * @param s message
     * @return answer object with message in the end
     */

    public AnswerDTO append(String s) {
        this.message += "\n\n" + s;
        return this;
    }

    public void setKeyboardType(KeyboardType keyboardType) {
        this.keyboardType = keyboardType;
    }

    /**
     * Method that appends message to the beginning skipping two lines of text
     *
     * @param s message
     * @return answer object with message in the beginning
     */

    public AnswerDTO appendToBeginning(String s) {
        this.message = s + "\n\n" + message;
        return this;
    }
}
