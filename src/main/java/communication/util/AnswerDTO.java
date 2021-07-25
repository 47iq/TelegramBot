package communication.util;

import communication.keyboard.KeyboardType;
import data.User;
import game.entity.CardName;
import org.jvnet.hk2.annotations.Optional;

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
    CardName cardName;
    User user;
    final boolean mustSend;

    /**
     * Constructor
     * @param isSuccessful is the answer successful
     * @param message      answer text
     * @param keyboardType keyboard type for a Telegram messenger
     * @param image        image that needs to be sent as well
     * @param buttons      buttons for a custom keyboard(if needed)
     * @param user
     * @param mustSend
     */

    public AnswerDTO(boolean isSuccessful, String message, KeyboardType keyboardType, @Optional File image, @Optional Map<String, String> buttons, User user, boolean mustSend) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.keyboardType = keyboardType;
        this.image = image;
        this.buttons = buttons;
        this.user = user;
        this.mustSend = mustSend;
    }

    public AnswerDTO(boolean mustSend) {
        this.mustSend = mustSend;
    }

    public CardName getCardName() {
        return cardName;
    }

    public void setCardName(CardName cardName) {
        this.cardName = cardName;
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

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "keyboardType=" + keyboardType +
                ", image=" + image +
                ", cardName=" + cardName +
                '}';
    }

    public boolean isMustSend() {
        return mustSend;
    }

    public User getUser() {
        return user;
    }
}
