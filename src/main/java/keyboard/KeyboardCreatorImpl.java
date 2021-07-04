package keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import util.MessageBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardCreatorImpl implements KeyboardCreator{

    List<String> defaultButtonTexts;
    private final int BUTTONS_IN_ROW = 2;
    private final int MAX_ROWS  = 5;

    public KeyboardCreatorImpl(List<String> buttons) {
        this.defaultButtonTexts = buttons;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(KeyboardType keyboard, Map<String, String> buttons) {
        if(buttons  != null)
            return getKeyboard(buttons);
        else
            return getKeyboard(keyboard);
    }

    private InlineKeyboardMarkup getKeyboard(KeyboardType type) {
        return switch (type) {
            case CLASSIC -> getClassicKeyboard(defaultButtonTexts);
            case MENU -> getMenuKeyboard();
            default -> null;
        };
    }

    private InlineKeyboardMarkup getMenuKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/view", MessageBundle.getMessage("/view"));
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    public InlineKeyboardMarkup getKeyboard(Map<String, String> buttonTexts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for(var text: buttonTexts.keySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonTexts.get(text));
            button.setCallbackData(text);
            buttons.add(button);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int rowCounter = 0;
        int counter = 0;
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (var button: buttons) {
            counter++;
            row.add(button);
            if(counter == BUTTONS_IN_ROW) {
                counter = 0;
                rows.add(row);
                row = new ArrayList<>();
                rowCounter++;
                if(rowCounter == MAX_ROWS)
                    break;
            }
        }
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getClassicKeyboard(List<String> buttonTexts) {
        Map<String, String> texts = new HashMap<>();
        for(var text : buttonTexts) {
            if(!text.equals("/start") && !text.equals("/view_card"))
                texts.put(text, MessageBundle.getMessage(text));
        }
        return getKeyboard(texts);
    }
}
