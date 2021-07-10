package communication.keyboard;

import org.jvnet.hk2.component.MultiMap;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import communication.util.MessageBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardCreatorImpl implements KeyboardCreator {

    private final int BUTTONS_IN_ROW = 2;
    private final int MAX_ROWS = 5;

    @Override
    public InlineKeyboardMarkup getKeyboard(KeyboardType keyboard, Map<String, String> buttons) {
        if (buttons != null)
            return getKeyboard(buttons);
        else
            return getKeyboard(keyboard);
    }

    private InlineKeyboardMarkup getKeyboard(KeyboardType type) {
        return switch (type) {
            case CLASSIC -> getClassicKeyboard();
            case MENU -> getMenuKeyboard();
            case WELCOME -> getWelcomeKeyboard();
            case SHOP -> getShopKeyboard();
            case LEAF -> getLeafKeyboard();
            case ITEM -> getItemKeyboard();
            case BATTLE -> getBattleKeyboard();
            case STATS -> getStatsKeyBoard();
            case DUNGEON -> getDungeonKeyboard();
            case DUNGEON_LEAF -> getDungeonLeafKeyboard();
            case DUNGEON_MENU -> getDungeonMenuKeyboard();
            case START_SHOP -> getStartShopKeyboard();
            default -> null;
        };
    }

    /**
     * Method that creates a keyboard for a start shop.
     *
     * @return a keyboard for a start shop
     * @see command.tutorial.StartShopCommand
     */

    private InlineKeyboardMarkup getStartShopKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/open_basic", MessageBundle.getMessage("info_openbasic"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a dungeon menu.
     *
     * @return a keyboard for a dungeon menu
     * @see command.main_menu.DungeonMenuCommand
     */

    private InlineKeyboardMarkup getDungeonMenuKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/help", MessageBundle.getMessage("back"));
        menu.put("/dungeon_enter", MessageBundle.getMessage("dungeon_enter"));
        menu.put("/dungeon_info", MessageBundle.getMessage("/info"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a dungeon with "back" button only.
     *
     * @return a keyboard for a dungeon with "back" button only
     */

    private InlineKeyboardMarkup getDungeonLeafKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/dungeon_leave", MessageBundle.getMessage("dungeon_leave"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a dungeon.
     *
     * @return a keyboard for a dungeon
     */

    private InlineKeyboardMarkup getDungeonKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/dungeon_next", MessageBundle.getMessage("dungeon_next"));
        menu.put("/dungeon_leave", MessageBundle.getMessage("dungeon_leave"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a stats menu.
     *
     * @return a keyboard for a stats menu
     * @see command.main_menu.StatsMenuCommand
     */

    private InlineKeyboardMarkup getStatsKeyBoard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/my_stats", MessageBundle.getMessage("info_mystats"));
        menu.put("/top_stats", MessageBundle.getMessage("info_top"));
        menu.put("/app_stats", MessageBundle.getMessage("info_app"));
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a battle menu.
     *
     * @return a keyboard for a battle menu
     * @see command.battle.BattleMenuCommand
     */

    private InlineKeyboardMarkup getBattleKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/battle", MessageBundle.getMessage("info_battle"));
        menu.put("/battle_info", MessageBundle.getMessage("/info"));
        menu.put("/leave_search", MessageBundle.getMessage("info_leavesearch"));
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for an item menu.
     *
     * @return a keyboard for an item menu
     * @see command.main_menu.UseItemMenuCommand
     */

    private InlineKeyboardMarkup getItemKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/use_heal", MessageBundle.getMessage("info_useheal"));
        menu.put("/use_boost", MessageBundle.getMessage("info_useboost"));
        menu.put("/item_info", MessageBundle.getMessage("/info"));
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard with back button only.
     *
     * @return a keyboard with back button only
     */

    private InlineKeyboardMarkup getLeafKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a shop menu.
     *
     * @return a keyboard for an shop menu
     * @see command.main_menu.ShopMenuCommand
     */

    private InlineKeyboardMarkup getShopKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/open_basic", MessageBundle.getMessage("info_openbasic"));
        menu.put("/open_advanced", MessageBundle.getMessage("info_openadvanced"));
        menu.put("/open_pro", MessageBundle.getMessage("info_openpro"));
        menu.put("/buy_boost", MessageBundle.getMessage("info_buyboost"));
        menu.put("/buy_heal", MessageBundle.getMessage("info_buyheal"));
        menu.put("/free_tokens", MessageBundle.getMessage("info_freetokens"));
        menu.put("/help", MessageBundle.getMessage("back"));
        menu.put("/buy_beer", MessageBundle.getMessage("info_buybeer"));
        menu.put("/shop_info", MessageBundle.getMessage("/info"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a welcome menu.
     *
     * @return a keyboard for a welcome menu
     * @see command.tutorial.StartCommand
     */

    private InlineKeyboardMarkup getWelcomeKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/start_shop", MessageBundle.getMessage("info_gotomenu"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for an item usage menu.
     *
     * @return a keyboard for an item usage menu
     * @see command.main_menu.UseItemMenuCommand
     */

    private InlineKeyboardMarkup getMenuKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/view", MessageBundle.getMessage("/view"));
        menu.put("/sell", MessageBundle.getMessage("info_sell"));
        menu.put("/help", MessageBundle.getMessage("back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a custom keyboard
     *
     * @param buttonTexts buttons of the keyboard
     * @return a keyboard
     */

    public InlineKeyboardMarkup getKeyboard(Map<String, String> buttonTexts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (var text : buttonTexts.keySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonTexts.get(text));
            button.setCallbackData(text);
            buttons.add(button);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int rowCounter = 0;
        int counter = 0;
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (var button : buttons) {
            counter++;
            row.add(button);
            if (counter == BUTTONS_IN_ROW) {
                counter = 0;
                rows.add(row);
                row = new ArrayList<>();
                rowCounter++;
                if (rowCounter == MAX_ROWS)
                    break;
            }
        }
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    /**
     * Method that creates a keyboard for a main menu.
     *
     * @return a keyboard for a main menu
     * @see command.service_command.HelpCommand
     */

    public InlineKeyboardMarkup getClassicKeyboard() {
        List<String> buttonTexts = new ArrayList<>();
        buttonTexts.add("/shop");
        buttonTexts.add("/stats");
        buttonTexts.add("/my_cards");
        buttonTexts.add("/use_item");
        buttonTexts.add("/battle_menu");
        buttonTexts.add("/dungeon_menu");
        buttonTexts.add("/info");
        Map<String, String> texts = new HashMap<>();
        for (var text : buttonTexts) {
            texts.put(text, MessageBundle.getMessage(text));
        }
        return getKeyboard(texts);
    }
}
