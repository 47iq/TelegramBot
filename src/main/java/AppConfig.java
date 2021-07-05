import command.*;
import command.admin.AddTokensCommand;
import command.admin.AppStatsCommand;
import command.admin.NotifyAllCommand;
import command.battle.BattleMenuCommand;
import command.battle.LeaveSearchCommand;
import command.battle.PrepareBattleCommand;
import command.battle.StartSearchCommand;
import command.card_collection.CardViewCommand;
import command.card_collection.NavigateToCardCommand;
import command.card_collection.SellCardCommand;
import command.card_collection.SellCommand;
import command.item.BoostCardCommand;
import command.item.HealCardCommand;
import command.item.UseBoostCommand;
import command.item.UseHealCommand;
import command.main_menu.StatsCommand;
import command.main_menu.UseItemCommand;
import command.service_command.*;
import command.main_menu.MyCardsCommand;
import command.main_menu.ShopCommand;
import command.shop.*;
import communication.connection.*;
import communication.notification.NotificationService;
import communication.notification.NotificationServiceImpl;
import data.*;
import game.*;
import communication.keyboard.KeyboardCreator;
import communication.keyboard.KeyboardCreatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import game.ImageIdentifier;
import communication.util.MessageFormatter;
import communication.util.MessageFormatterImpl;

import java.util.*;

@Configuration
public class AppConfig {

    Map<String, Command> commandMap = new HashMap<>();

    @Bean
    public TelegramLongPollingBot getBot()  {
        return new TelegramBot();
    }

    @Bean
    @Scope("singleton")
    public UserDAO getUserDAO() {
        return new PSQLUserDAO();
    }

    @Bean
    @Scope("singleton")
    public CardDAO getCardDAO() {
        return new PSQLCardDAO();
    }

    @Bean
    @Scope("singleton")
    public CommandFactory getCommandFactory(){
        commandMap.put("/register", getRegisterCommand());
        commandMap.put("/help", getHelpCommand());
        commandMap.put("/start", getStartCommand());
        commandMap.put("/open_basic", getOpenBasicCommand());
        commandMap.put("/open_advanced", getOpenAdvancedCommand());
        commandMap.put("/open_pro", getOpenProCommand());
        commandMap.put("/my_cards", getMyCardsCommand());
        commandMap.put("/view", getViewCommand());
        commandMap.put("/view_card", getNavigateToCardCommand());
        commandMap.put("/shop", getShopCommand());
        commandMap.put("/buy_boost", getBuyBoostCommand());
        commandMap.put("/stats", getStatsCommand());
        commandMap.put("/buy_heal", getBuyHealCommand());
        commandMap.put("/free_tokens", getFreeTokensCommand());
        commandMap.put("/use_item", getUseItemCommand());
        commandMap.put("/use_heal", getUseHealCommand());
        commandMap.put("/use_boost", getUseBoostCommand());
        commandMap.put("/heal_card", getHealCardCommand());
        commandMap.put("/boost_card", getBoostCardCommand());
        commandMap.put("/buy_beer", getBuyBeerCommand());
        commandMap.put("/sell", getSellCommand());
        commandMap.put("/battle_card", getStartSearchCommand());
        commandMap.put("/battle", getPrepareSearchCommand());
        commandMap.put("/sell_card", getSellCardCommand());
        commandMap.put("/leave_search", getLeaveSearchCommand());
        commandMap.put("/battle_menu", getBattleMenuCommand());
        Map<String, Command> adminCommands = new HashMap<>();
        adminCommands.put("/add_tokens", getAddTokensCommand());
        adminCommands.put("/notify_all", getNotifyAllCommand());
        adminCommands.put("/app_stats", getAppStatsCommand());
        return new CommandFactoryImpl(commandMap, adminCommands);
    }

    @Bean
    @Scope("singleton")
    public Command getAppStatsCommand() {
        return new AppStatsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getNotifyAllCommand() {
        return new NotifyAllCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBattleMenuCommand() {
        return new BattleMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getLeaveSearchCommand() {
        return new LeaveSearchCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getStartSearchCommand() {
        return new StartSearchCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getPrepareSearchCommand() {
        return new PrepareBattleCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getSellCommand() {
        return new SellCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getSellCardCommand() {
        return new SellCardCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBuyBeerCommand() {
        return new BuyBeerCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getHealCardCommand() {
        return new HealCardCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBoostCardCommand() {
        return new BoostCardCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getUseItemCommand() {
        return new UseItemCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getAddTokensCommand() {
        return new AddTokensCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getUseHealCommand() {
        return new UseHealCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getUseBoostCommand() {
        return new UseBoostCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getOpenAdvancedCommand() {
        return new OpenAdvancedBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getStatsCommand() {
        return new StatsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getOpenProCommand() {
        return new OpenProBoxCommand();
    }


    @Bean
    @Scope("singleton")
    public Command getShopCommand() {
        return new ShopCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBuyBoostCommand() {
        return new BuyBoostCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBuyHealCommand() {
        return new BuyHealCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getFreeTokensCommand() {
        return new DailyFreeTokensCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getMyCardsCommand() {
        return new MyCardsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getStartCommand() {
        return new StartCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getViewCommand() {
        return new CardViewCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getNavigateToCardCommand() {
        return new NavigateToCardCommand();
    }

    @Bean
    public Command getOpenBasicCommand() {
        return new OpenBasicBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public HelpCommand getHelpCommand() {
        return new HelpCommand();
    }

    @Bean
    @Scope("singleton")
    public AnswerService getAnswerService() {
        return new AnswerServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public ImageService getImageService() {
        return new ImageServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public ImageBase getImageBase(){
        Map<ImageIdentifier, String> pathMap = new HashMap<>();
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.BASIC), settings.getString("KLIMENKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.RARE), settings.getString("KLIMENKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.EPIC), settings.getString("KLIMENKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.LEGENDARY), settings.getString("KLIMENKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.BASIC), settings.getString("KOROBKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.RARE), settings.getString("KOROBKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.EPIC), settings.getString("KOROBKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.LEGENDARY), settings.getString("KOROBKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.BASIC), settings.getString("GAVRILOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.RARE), settings.getString("GAVRILOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.EPIC), settings.getString("GAVRILOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.LEGENDARY), settings.getString("GAVRILOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.BASIC), settings.getString("BALAKSHIN_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.RARE), settings.getString("BALAKSHIN_RARE"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.EPIC), settings.getString("BALAKSHIN_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.LEGENDARY), settings.getString("BALAKSHIN_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.BASIC), settings.getString("POLYAKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.RARE), settings.getString("POLYAKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.EPIC), settings.getString("POLYAKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.LEGENDARY), settings.getString("POLYAKOV_LEGENDARY"));
        return new ImageBaseImpl(pathMap);
    }

    @Bean
    @Scope("singleton")
    public KeyboardCreator getKeyboardCreator() {
        return new KeyboardCreatorImpl();
    }

    @Bean
    @Scope("singleton")
    public MessageFormatter messageFormatter(){
        return new MessageFormatterImpl();
    }

    @Bean
    @Scope("singleton")
    public OpenBoxCommand openBoxCommand() {
        return new OpenBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public LootBox getLootBox() {
        return new LootBoxImpl();
    }

    @Bean
    @Scope("singleton")
    public CardService getCardService() {
        return new CardServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public PriceCalculator getPriceCalculator() {
        return new PriceCalculatorImpl();
    }

    @Bean
    @Scope("singleton")
    public NotificationService notificationService() {
        return new NotificationServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public BattleService battleService() {
        return new BattleServiceImpl();
    }

    @Bean
    public RegisterCommand getRegisterCommand(){
        return new RegisterCommand();
    }
}
