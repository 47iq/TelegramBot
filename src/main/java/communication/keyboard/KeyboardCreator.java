package communication.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;

public interface KeyboardCreator {
    InlineKeyboardMarkup getKeyboard(KeyboardType keyboard, Map<String, String> buttons);
}
