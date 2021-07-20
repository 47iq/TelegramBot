import command.*;
import command.admin.AddTokensCommand;
import command.admin.AllUsersCommand;
import command.battle.*;
import command.dungeon.*;
import command.item.*;
import command.stats.AppStatsCommand;
import command.admin.GetUserStatsCommand;
import command.admin.NotifyAllCommand;
import command.card_collection.CardViewCommand;
import command.card_collection.NavigateToCardCommand;
import command.card_collection.SellCardCommand;
import command.card_collection.SellCommand;
import command.main_menu.*;
import command.stats.TopStatsCommand;
import command.stats.MyStatsCommand;
import command.service_command.*;
import command.shop.*;
import command.tutorial.StartCommand;
import command.tutorial.StartShopCommand;
import communication.connection.*;
import communication.notification.NotificationService;
import communication.notification.NotificationServiceImpl;
import util.MessageBundle;
import data.*;
import communication.keyboard.KeyboardCreator;
import communication.keyboard.KeyboardCreatorImpl;
import game.dungeon.*;
import game.entity.*;
import game.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import util.MessageFormatter;
import util.MessageFormatterImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.*;

/**
 * Config class for Spring framework
 */

@Configuration
//@ComponentScan

public class AppConfig {

    Map<String, Command> commandMap = new HashMap<>();

    @Bean
    @Scope("singleton")
    @Qualifier("cave_random")
    public WeightedRandomizer<Class<? extends Cave>> caveWeightedRandomizer(){
        Map<Class<? extends Cave>, Double> weights  =  new HashMap<>();
        weights.put(RobberyCave.class, 10.0);
        weights.put(HealCave.class, 10.0);
        weights.put(LootCave.class, 10.0);
        weights.put(TrapCave.class, 10.0);
        weights.put(BattleCave.class, 60.0);
        weights.put(ArmorCave.class, 1.0);
        weights.put(WeaponCave.class, 1.0);
        weights.put(LevelUpCave.class, 0.5);
        weights.put(LootBoxCave.class, 0.25);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope
    @Qualifier("enemy_random")
    public WeightedRandomizer<EnemyType> enemyWeightedRandomizer() {
        Map<EnemyType, Double> weights = new HashMap<>();
        weights.put(EnemyType.ANGRY_STUDENTS, 15.0);
        weights.put(EnemyType.ROBOT, 10.0);
        weights.put(EnemyType.LARY, 10.0);
        weights.put(EnemyType.PROGRAMMING_LAB, 12.0);
        weights.put(EnemyType.ANDREW, 10.0);
        weights.put(EnemyType.WOLF, 12.0);
        weights.put(EnemyType.RAD_COCKROACH, 10.0);
        weights.put(EnemyType.VIETNAM_GUY, 12.0);
        weights.put(EnemyType.DUNGEON_MASTER, 12.0);
        weights.put(EnemyType.STUDENT_OFFICE, 12.0);
        weights.put(EnemyType.EXPELLED_STUDENT, 10.0);
        weights.put(EnemyType.PLASTIC_WORLD, 12.0);
        weights.put(EnemyType.DEV, 8.0);
        weights.put(EnemyType.RECTOR, 2.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    @Qualifier("card_random")
    public WeightedRandomizer<CardName> cardWeightedRandomizer() {
        Map<CardName, Double> weights = new HashMap<>();
        weights.put(CardName.KLIMENKOV, 1.0);
        weights.put(CardName.KOROBKOV, 1.0);
        weights.put(CardName.POLYAKOV, 1.0);
        weights.put(CardName.BALAKSHIN, 1.0);
        weights.put(CardName.GAVRILOV, 1.0);
        weights.put(CardName.PERTSEV, 1.0);
        weights.put(CardName.VOZIANOVA, 1.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    @Qualifier("elite_card_random")
    public WeightedRandomizer<CardName> eliteCardWeightedRandomizer() {
        Map<CardName, Double> weights = new HashMap<>();
        weights.put(CardName.BILLIE_HARRINGTON, 1.0);
        weights.put(CardName.STANKEVICH, 1.0);
        weights.put(CardName.SVYATOSLAV, 1.0);
        return new WeightedRandomizerImpl<>(weights);
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
        commandMap.put("/app_stats", getAppStatsCommand());
        commandMap.put("/top_stats", getGlobalStatsCommand());
        commandMap.put("/my_stats", getMyStatsCommand());
        commandMap.put("/dungeon_menu", getDungeonMenuCommand());
        commandMap.put("/dungeon_leave", getDungeonLeaveCommand());
        commandMap.put("/dungeon_enter", getDungeonEnterCommand());
        commandMap.put("/dungeon_enter_card", getDungeonEnterCardCommand());
        commandMap.put("/dungeon_next", getDungeonNextCommand());
        commandMap.put("/info", getInfoCommand());
        commandMap.put("/item_info", getItemInfoCommand());
        commandMap.put("/dungeon_info", getDungeonInfoCommand());
        commandMap.put("/shop_info", getShopInfoCommand());
        commandMap.put("/battle_info", getBattleInfoCommand());
        commandMap.put("/start_shop", getStartShopCommand());
        commandMap.put("/instant_heal", getInstantHealCommand());
        commandMap.put("/buy_item", getBuyItemCommand());
        commandMap.put("/buy_box",  getBuyBoxCommand());
        Map<String, Command> adminCommands = new HashMap<>();
        adminCommands.put("/add_tokens", getAddTokensCommand());
        adminCommands.put("/notify_all", getNotifyAllCommand());
        adminCommands.put("/user_stats", getUserStatsCommand());
        adminCommands.put("/all_users", getUsersStatsCommand());
        return new CommandFactoryImpl(commandMap, adminCommands);
    }

    @Bean
    @Scope("singleton")
    public Command getBuyItemCommand() {
        return new BuyItemMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBuyBoxCommand() {
        return new BuyLootBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getInstantHealCommand() {
        return new InstantHealCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getBattleInfoCommand() {
        return new BattleInfoCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getStartShopCommand() {
        return new StartShopCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getShopInfoCommand() {
        return new ShopInfoCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonInfoCommand() {
        return new DungeonInfoCommand();
    }


    @Bean
    @Scope("singleton")
    public Command getItemInfoCommand() {
        return new ItemInfoCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getInfoCommand() {
        return new InfoCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonEnterCardCommand() {
        return new EnterDungeonCardCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonLeaveCommand() {
        return new LeaveDungeonCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonMenuCommand() {
        return new DungeonMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonNextCommand() {
        return new EnterNextCaveCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getDungeonEnterCommand() {
        return new EnterDungeonCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getMyStatsCommand() {
        return new MyStatsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getGlobalStatsCommand() {
        return new TopStatsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getUserStatsCommand() {
        return new GetUserStatsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getUsersStatsCommand() {
        return new AllUsersCommand();
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
        return new UseItemMenuCommand();
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
        return new StatsMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getOpenProCommand() {
        return new OpenProBoxCommand();
    }


    @Bean
    @Scope("singleton")
    public Command getShopCommand() {
        return new ShopMenuCommand();
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
        return new MyCardsMenuCommand();
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
    public ImageSenderService getImageService() {
        return new ImageSenderServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public ImageParser getImageBase(){
        Map<ImageIdentifier, String> pathMap = new HashMap<>();
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.BASIC), MessageBundle.getSetting("KLIMENKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.RARE), MessageBundle.getSetting("KLIMENKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.EPIC), MessageBundle.getSetting("KLIMENKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.LEGENDARY), MessageBundle.getSetting("KLIMENKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.BASIC), MessageBundle.getSetting("KOROBKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.RARE), MessageBundle.getSetting("KOROBKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.EPIC), MessageBundle.getSetting("KOROBKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.LEGENDARY), MessageBundle.getSetting("KOROBKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.BASIC), MessageBundle.getSetting("GAVRILOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.RARE), MessageBundle.getSetting("GAVRILOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.EPIC), MessageBundle.getSetting("GAVRILOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.LEGENDARY), MessageBundle.getSetting("GAVRILOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.BASIC), MessageBundle.getSetting("BALAKSHIN_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.RARE), MessageBundle.getSetting("BALAKSHIN_RARE"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.EPIC), MessageBundle.getSetting("BALAKSHIN_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.LEGENDARY), MessageBundle.getSetting("BALAKSHIN_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.BASIC), MessageBundle.getSetting("POLYAKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.RARE), MessageBundle.getSetting("POLYAKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.EPIC), MessageBundle.getSetting("POLYAKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.LEGENDARY), MessageBundle.getSetting("POLYAKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.VOZIANOVA, CardType.BASIC), MessageBundle.getSetting("VOZIANOVA"));
        pathMap.put(new ImageIdentifier(CardName.VOZIANOVA, CardType.RARE), MessageBundle.getSetting("VOZIANOVA"));
        pathMap.put(new ImageIdentifier(CardName.VOZIANOVA, CardType.EPIC), MessageBundle.getSetting("VOZIANOVA"));
        pathMap.put(new ImageIdentifier(CardName.VOZIANOVA, CardType.LEGENDARY), MessageBundle.getSetting("VOZIANOVA"));
        pathMap.put(new ImageIdentifier(CardName.PERTSEV, CardType.BASIC), MessageBundle.getSetting("PERTSEV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.PERTSEV, CardType.RARE), MessageBundle.getSetting("PERTSEV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.PERTSEV, CardType.EPIC), MessageBundle.getSetting("PERTSEV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.PERTSEV, CardType.LEGENDARY), MessageBundle.getSetting("PERTSEV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.BILLIE_HARRINGTON, CardType.LEGENDARY), MessageBundle.getSetting("BILLIE_HARRINGTON_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.SVYATOSLAV, CardType.LEGENDARY), MessageBundle.getSetting("SVYATOSLAV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.STANKEVICH, CardType.LEGENDARY), MessageBundle.getSetting("STANKEVICH_LEGENDARY"));
        return new ImageParserImpl(pathMap);
    }

    @Bean
    @Scope("singleton")
    public KeyboardCreator getKeyboardCreator() {
        return new KeyboardCreatorImpl();
    }

    @Bean
    @Scope("singleton")
    public CaveService getCaveService() {
        return new CaveServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public OpenSuperRareBoxCommand getBox() {
        return new OpenSuperRareBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public TelegramLongPollingBot getBot() {
        return new TelegramBot();
    }

    @Bean
    @Scope("singleton")
    public TextSenderService getAnswerService() {
        return new TextSenderServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public CardService getCardService() {
        return new CardServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public BattleXpCalculator getXpCalc() {
        return new BattleXpCalculatorImpl();
    }

    @Bean
    @Scope("singleton")
    public MessageFormatter getMessageFormatter(){
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

    @Bean
    public OpenBoxCommand  openSuperRareBoxCommand()  {
        return new OpenBoxCommand();
    }
}
