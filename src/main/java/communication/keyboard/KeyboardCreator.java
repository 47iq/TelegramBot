package communication.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;

/**
 * Interface of a keyboard creator for a Telegram messenger
 */
public interface KeyboardCreator {

    /**
     * Method that creates a keyboard for a Telegram messenger.
     *
     * @param keyboard keyboard type
     * @param buttons  buttons for a custom keyboard
     * @return keyboard usable in a telegram answer.
     */
    InlineKeyboardMarkup getKeyboard(KeyboardType keyboard, Map<String, String> buttons);
}
