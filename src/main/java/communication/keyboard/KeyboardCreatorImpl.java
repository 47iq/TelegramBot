package communication.keyboard;

import command.dungeon.DungeonMenuCommand;
import game.entity.Card;
import game.entity.User;
import game.quest.QuestService;
import game.service.UserService;
import game.battle.AttackType;
import game.battle.DefenceType;
import game.dungeon.CaveService;
import game.marketplace.MarketplaceService;
import game.service.BattleService;
import game.service.EventType;
import game.service.NotificationPublisher;
import game.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import util.MessageBundle;

import java.util.*;

@Component
public class KeyboardCreatorImpl implements KeyboardCreator {

    @Autowired
    UserService userService;
    @Autowired
    CaveService caveService;
    @Autowired
    BattleService battleService;
    @Autowired
    NotificationPublisher notificationPublisher;
    @Autowired
    MarketplaceService marketplaceService;
    @Autowired
    TaskService taskService;
    @Autowired
    QuestService questService;

    private final int BUTTONS_IN_ROW = 2;
    private final int MAX_ROWS = 5;

    @Override
    public InlineKeyboardMarkup getKeyboard(KeyboardType keyboard, Map<String, String> buttons, User user) {
        if (buttons != null)
            return getKeyboard(buttons);
        else
            return getKeyboard(keyboard, user);
    }

    private InlineKeyboardMarkup getKeyboard(KeyboardType type, User user) {
        return switch (type) {
            case MENU -> getClassicKeyboard(user);
            case CARDS -> getMenuKeyboard();
            case WELCOME -> getWelcomeKeyboard();
            case SHOP -> getShopKeyboard(user);
            case LEAF -> getLeafKeyboard();
            case ITEM -> getItemKeyboard(user);
            case BATTLE -> getBattleKeyboard(user);
            case STATS -> getStatsKeyBoard();
            case DUNGEON -> getDungeonKeyboard();
            case DUNGEON_LEAF -> getDungeonLeafKeyboard(user);
            case DUNGEON_MENU -> getDungeonMenuKeyboard(user);
            case START_SHOP -> getStartShopKeyboard();
            case BUY_BOX -> getBuyBoxKeyboard();
            case BUY_ITEM -> getBuyItemKeyboard();
            case ADMIN -> getAdminKeyboard();
            case BATTLE_ATTACK -> getAttackKeyboard();
            case BATTLE_DEFENCE -> getDefenceKeyboard();
            case SEARCH_LEAF -> getSearchLeafKeyboard();
            case MARKETPLACE -> getMarketplaceKeyboard(user);
            case QUEST_MENU, QUEST_LEAF, QUEST_FINISH -> getQuestMenuKeyboard(user);
            case QUEST -> getQuestKeyboard(user);
            case QUEST_SHOP -> getQuestShopMenu(user);
            case PVE_MENU -> getPVEMenu(user);
            default -> null;
        };
    }

    private InlineKeyboardMarkup getPVEMenu(User user) {
        Map<String, String> menu = new HashMap<>();
        menu.put("/dungeon_menu", MessageBundle.getMessage("/dungeon_menu"));
        menu.put("/quest_menu", MessageBundle.getMessage("/quest_menu"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getQuestShopMenu(User user) {
        Map<String, String> menu = new HashMap<>();
        Card card = questService.getCard(user);
        if(card.getHealth() != card.getMaxHealth()) {
            if (userService.getHealCount(user) == 0)
                menu.put("/buy_use_heal", MessageBundle.getMessage("quest_buy.heal"));
            else
                menu.put("/quest_instant_heal", MessageBundle.getMessage("quest_heal"));
        }
        menu.put("/change", MessageBundle.getMessage("quest_change"));
        if(card.getHealth() > 0)
            menu.put("/quest_resume", MessageBundle.getMessage("quest_resume"));
        menu.put("/quest_menu", MessageBundle.getMessage("quest_back"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getQuestKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        menu.put("/quest_resume", MessageBundle.getMessage("quest_resume"));
        menu.put("/quest_menu", MessageBundle.getMessage("quest_back"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getQuestMenuKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        if(questService.isInQuest(user))
            menu.put("/quest_resume", MessageBundle.getMessage("quest_resume.save"));
        else
            menu.put("/start_first", MessageBundle.getMessage("info_first.quest.start"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getMarketplaceKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        if(marketplaceService.isPresent(user))
            menu.put("/cancel", MessageBundle.getMessage("/marketplace_cancel"));
        menu.put("/list", MessageBundle.getMessage("/marketplace_list"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        menu.put("/buy", MessageBundle.getMessage("/marketplace_buy"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getSearchLeafKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/leave_search", MessageBundle.getMessage("/leave_search"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getDefenceKeyboard() {
        Map<String, String> menu = new HashMap<>();
        Arrays.stream(DefenceType.values()).forEach(x-> menu.put("/set_defence." + x.name().toLowerCase(), MessageBundle.getMessage("info_defence." + x.name().toLowerCase())));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getAttackKeyboard() {
        Map<String, String> menu = new HashMap<>();
        Arrays.stream(AttackType.values()).forEach(x-> menu.put("/set_attack." + x.name().toLowerCase(), MessageBundle.getMessage("info_attack." + x.name().toLowerCase())));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getAdminKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/all_users", MessageBundle.getMessage("/all_users"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getBuyItemKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/buy_boost", MessageBundle.getMessage("/buy_boost"));
        menu.put("/buy_heal", MessageBundle.getMessage("/buy_heal"));
        menu.put("/buy_beer", MessageBundle.getMessage("/buy_beer"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    private InlineKeyboardMarkup getBuyBoxKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/open_basic", MessageBundle.getMessage("/open_basic"));
        menu.put("/open_advanced", MessageBundle.getMessage("/open_advanced"));
        menu.put("/open_pro", MessageBundle.getMessage("/open_pro"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a start shop.
     *
     * @return a keyboard for a start shop
     * @see command.tutorial.StartShopCommand
     */

    private InlineKeyboardMarkup getStartShopKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/open_basic", MessageBundle.getMessage("/open_basic"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a dungeon menu.
     *
     * @return a keyboard for a dungeon menu
     * @see DungeonMenuCommand
     * @param user
     */

    private InlineKeyboardMarkup getDungeonMenuKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        menu.put("/dungeon_enter", MessageBundle.getMessage("dungeon_enter"));
        if(!taskService.getAll(user).isEmpty())
            menu.put("/tasks", MessageBundle.getMessage("/tasks"));
        menu.put("/dungeon_info", MessageBundle.getMessage("/info"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a dungeon with "back" button only.
     *
     * @return a keyboard for a dungeon with "back" button only
     * @param user user
     */

    private InlineKeyboardMarkup getDungeonLeafKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        if(userService.getHealCount(user) > 0)
            menu.put("/instant_heal." + caveService.getCard(user).getUID(), MessageBundle.getMessage("instant_heal"));
        else
            menu.put("/shop", MessageBundle.getMessage("instant_shop"));
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
        menu.put("/my_stats", MessageBundle.getMessage("stats_my.stats"));
        menu.put("/top_stats", MessageBundle.getMessage("stats_top"));
        menu.put("/app_stats", MessageBundle.getMessage("stats_app"));
        menu.put("/achievements", MessageBundle.getMessage("/achievements"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a battle menu.
     *
     * @return a keyboard for a battle menu
     * @see command.battle.BattleMenuCommand
     */

    private InlineKeyboardMarkup getBattleKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        menu.put("/battle_info", MessageBundle.getMessage("/info"));
        if(battleService.isBattling(user))
            menu.put("/leave_search", MessageBundle.getMessage("/leave_search"));
        else
            menu.put("/battle", MessageBundle.getMessage("/start_search"));
        if(notificationPublisher.isSubscribed(user, EventType.BATTLE_ENEMY))
            menu.put("/unsubscribe_battle", MessageBundle.getMessage("/unsubscribe_battle"));
        else
            menu.put("/subscribe_battle", MessageBundle.getMessage("/subscribe_battle"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for an item menu.
     *
     * @return a keyboard for an item menu
     * @see command.main_menu.UseItemMenuCommand
     * @param user user
     */

    private InlineKeyboardMarkup getItemKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        if(userService.getHealCount(user) > 0)
            menu.put("/use_heal", MessageBundle.getMessage("/use_heal"));
        if(userService.getBoostCount(user) > 0)
            menu.put("/use_boost", MessageBundle.getMessage("/use_boost"));
        if(userService.getHealCount(user) <= 0 || userService.getBoostCount(user) <= 0)
            menu.put("/shop", MessageBundle.getMessage("instant_shop"));
        menu.put("/item_info", MessageBundle.getMessage("/info"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard with back button only.
     *
     * @return a keyboard with back button only
     */

    private InlineKeyboardMarkup getLeafKeyboard() {
        Map<String, String> menu = new HashMap<>();
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a keyboard for a shop menu.
     *
     * @return a keyboard for an shop menu
     * @see command.main_menu.ShopMenuCommand
     * @param user
     */

    private InlineKeyboardMarkup getShopKeyboard(User user) {
        Map<String, String> menu = new HashMap<>();
        menu.put("/buy_box", MessageBundle.getMessage("info_buybox"));
        menu.put("/buy_item", MessageBundle.getMessage("info_buyitem"));
        if (userService.canRedeemTokens(user))
        menu.put("/free_tokens", MessageBundle.getMessage("shop_freetokens"));
        menu.put("/marketplace", MessageBundle.getMessage("/marketplace"));
        menu.put("/shop_info", MessageBundle.getMessage("/info"));
        menu.put("/back", MessageBundle.getMessage("/back"));
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
        menu.put("/start_shop", MessageBundle.getMessage("start_goto.menu"));
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
        menu.put("/use_item", MessageBundle.getMessage("/use_item"));
        menu.put("/back", MessageBundle.getMessage("/back"));
        return getKeyboard(menu);
    }

    /**
     * Method that creates a custom keyboard
     *
     * @param buttonTexts buttons of the keyboard
     * @return a keyboard
     */

    public InlineKeyboardMarkup getKeyboard(Map<String, String> buttonTexts) {
        boolean hasHelpCommand = false;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (var text : buttonTexts.keySet()) {
            if(!text.equals("/help") && !text.equals("/back")) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(buttonTexts.get(text));
                button.setCallbackData(text);
                buttons.add(button);
            } else
                hasHelpCommand = true;
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
        if(hasHelpCommand) {
            row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(MessageBundle.getMessage("/back"));
            button.setCallbackData("/back");
            row.add(button);
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    /**
     * Method that creates a keyboard for a main menu.
     *
     * @return a keyboard for a main menu
     * @see command.service_command.HelpCommand
     */

    public InlineKeyboardMarkup getClassicKeyboard(User user) {
        List<String> buttonTexts = new ArrayList<>();
        buttonTexts.add("/shop");
        buttonTexts.add("/stats");
        buttonTexts.add("/my_cards");
        buttonTexts.add("/use_item");
        buttonTexts.add("/battle_menu");
        buttonTexts.add("/pve_menu");
        buttonTexts.add("/info");
        if(user.getUID().equals(MessageBundle.getSetting("ADMIN_UID")))
            buttonTexts.add("/admin");
        Map<String, String> texts = new HashMap<>();
        for (var text : buttonTexts) {
            texts.put(text, MessageBundle.getMessage(text));
        }
        return getKeyboard(texts);
    }
}
