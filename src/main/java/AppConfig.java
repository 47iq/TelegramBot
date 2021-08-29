import command.*;
import command.admin.*;
import command.battle.*;
import command.dungeon.*;
import command.item.*;
import command.marketplace.*;
import command.quest.*;
import command.stats.AchievementStatsCommand;
import command.stats.AppStatsCommand;
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
import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.notification.NotificationServiceImpl;
import game.battle.BattleXpCalculator;
import game.battle.BattleXpCalculatorImpl;
import game.marketplace.MarketplaceService;
import game.marketplace.MarketplaceServiceImpl;
import game.quest.*;
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
    Quest firstQuest() {
        return new FirstQuest();
    }

    @Bean
    @Scope("singleton")
    QuestDAO questDAO() {
        return new PQSLQuestDAO();
    }

    @Bean
    @Scope("singleton")
    QuestService questService() {
        return new QuestServiceImpl();
    }

    @Bean(name="first_actions")
    @Scope("singleton")
    public Map<Long, QuestStepAction> questStageActionMap() {
        Map<Long, QuestStepAction> map = new HashMap<>();
        map.put(1L, QuestStepAction.MESSAGE);
        map.put(2L, QuestStepAction.SHOP);
        map.put(3L, QuestStepAction.BATTLE);
        map.put(4L, QuestStepAction.BATTLE);
        map.put(5L, QuestStepAction.MESSAGE);
        map.put(6L, QuestStepAction.SHOP);
        map.put(7L, QuestStepAction.BATTLE);
        map.put(8L, QuestStepAction.BATTLE);
        map.put(9L, QuestStepAction.MESSAGE);
        map.put(10L, QuestStepAction.SHOP);
        map.put(11L, QuestStepAction.BATTLE);
        map.put(12L, QuestStepAction.BATTLE);
        map.put(13L, QuestStepAction.MESSAGE);
        map.put(14L, QuestStepAction.SHOP);
        map.put(15L, QuestStepAction.BATTLE);
        map.put(16L, QuestStepAction.BATTLE);
        map.put(17L, QuestStepAction.BATTLE);
        map.put(18L, QuestStepAction.MESSAGE);
        return map;
    }

    @Bean(name="first_enemies")
    @Scope("singleton")
    Map<Long, EnemyType> questEnemyTypeMap() {
        Map<Long, EnemyType> map = new HashMap<>();
        map.put(3L, EnemyType.FIRST_QUEST_1);
        map.put(4L, EnemyType.FIRST_QUEST_2);
        map.put(7L, EnemyType.FIRST_QUEST_3);
        map.put(8L, EnemyType.FIRST_QUEST_4);
        map.put(11L, EnemyType.FIRST_QUEST_5);
        map.put(12L, EnemyType.FIRST_QUEST_6);
        map.put(15L, EnemyType.FIRST_QUEST_7);
        map.put(16L, EnemyType.FIRST_QUEST_8);
        map.put(17L, EnemyType.FIRST_QUEST_9);
        return map;
    }

    @Bean(name="first_stages")
    @Scope("singleton")
    NavigableSet<Long> stages() {
        NavigableSet<Long> stages = new TreeSet<>();
        stages.add(1L);
        stages.add(5L);
        stages.add(9L);
        stages.add(13L);
        return stages;
    }

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
        weights.put(ArmorCave.class, 0.5);
        weights.put(WeaponCave.class, 1.0);
        weights.put(LevelUpCave.class, 0.5);
        weights.put(LootBoxCave.class, 0.2);
        weights.put(TaskCave.class, 1.0);
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
    public WeightedRandomizer<Integer> taskLevelWeightedRandomizer() {
        Map<Integer, Double> weights = new HashMap<>();
        weights.put(1, 25.0);
        weights.put(2, 20.0);
        weights.put(3, 15.0);
        weights.put(4, 10.0);
        weights.put(5, 5.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    public WeightedRandomizer<TaskType> taskTypeWeightedRandomizer() {
        Map<TaskType, Double> weights = new HashMap<>();
        weights.put(TaskType.CAVE, 2.0);
        weights.put(TaskType.BATTLE, 2.0);
        weights.put(TaskType.PVP_BATTLE, 1.0);
        weights.put(TaskType.MONEY_EARNED, 2.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    public WeightedRandomizer<RewardType> rewardTypeWeightedRandomizer() {
        Map<RewardType, Double> weights = new HashMap<>();
        weights.put(RewardType.HEAL, 10.0);
        weights.put(RewardType.MONEY, 10.0);
        weights.put(RewardType.LOOT_BOX, 2.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    @Qualifier("task_card_random")
    public WeightedRandomizer<CardName> taskCardWeightedRandomizer() {
        Map<CardName, Double> weights = new HashMap<>();
        weights.put(CardName.TASK_1, 1.0);
        weights.put(CardName.TASK_2, 1.0);
        return new WeightedRandomizerImpl<>(weights);
    }

    @Bean
    @Scope("singleton")
    public TaskDAO taskDAO() {
        return new PSQLTaskDAO();
    }

    @Bean
    @Scope("singleton")
    public TaskService taskService() {
        return new TaskServiceImpl();
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
    @Scope
    @Qualifier("enemy_random")
    public Map<Command, KeyboardType> keyboardTypeMap() {
        Map<Command, KeyboardType> keyboardTypeMap = new HashMap<>();
        keyboardTypeMap.put(getRegisterCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getHelpCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getBackCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getStartCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getOpenBasicCommand(), KeyboardType.BUY_BOX);
        keyboardTypeMap.put(getOpenAdvancedCommand(), KeyboardType.BUY_BOX);
        keyboardTypeMap.put(getOpenProCommand(), KeyboardType.BUY_BOX);
        keyboardTypeMap.put(getMyCardsCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getViewCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getNavigateToCardCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getShopCommand(), KeyboardType.CARDS);
        keyboardTypeMap.put(getBuyBoostCommand(), KeyboardType.BUY_ITEM);
        keyboardTypeMap.put(getStatsCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getBuyHealCommand(), KeyboardType.BUY_ITEM);
        keyboardTypeMap.put(getFreeTokensCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getUseItemCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getUseHealCommand(), KeyboardType.ITEM);
        keyboardTypeMap.put(getUseBoostCommand(), KeyboardType.ITEM);
        keyboardTypeMap.put(getHealCardCommand(), KeyboardType.ITEM);
        keyboardTypeMap.put(getBoostCardCommand(), KeyboardType.ITEM);
        keyboardTypeMap.put(getBuyBeerCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getSellCommand(), KeyboardType.CARDS);
        keyboardTypeMap.put(getStartSearchCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getPrepareSearchCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getSellCardCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getLeaveSearchCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getBattleMenuCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getAppStatsCommand(), KeyboardType.STATS);
        keyboardTypeMap.put(getGlobalStatsCommand(), KeyboardType.STATS);
        keyboardTypeMap.put(getMyStatsCommand(), KeyboardType.STATS);
        keyboardTypeMap.put(getDungeonMenuCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getDungeonLeaveCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getDungeonEnterCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getDungeonEnterCardCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getDungeonNextCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getInfoCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getItemInfoCommand(), KeyboardType.ITEM);
        keyboardTypeMap.put(getDungeonInfoCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getShopInfoCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getBattleInfoCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getStartShopCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getInstantHealCommand(), KeyboardType.QUEST);
        keyboardTypeMap.put(getBuyItemCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getBuyBoxCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getAchievementsCommand(), KeyboardType.STATS);
        keyboardTypeMap.put(getSetAttackCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getSetDefenceCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getBattleSubscribeCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getBattleUnsubscribeCommand(), KeyboardType.BATTLE);
        keyboardTypeMap.put(getBuyCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getBuyCardCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getListCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getListCardCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getCancelCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getCancelCardCommand(), KeyboardType.MARKETPLACE);
        keyboardTypeMap.put(getMarketplaceCommand(), KeyboardType.SHOP);
        keyboardTypeMap.put(getTasksCommand(), KeyboardType.DUNGEON_MENU);
        keyboardTypeMap.put(getContinueCommand(), KeyboardType.QUEST_MENU);
        keyboardTypeMap.put(getChangeCommand(), KeyboardType.QUEST_MENU);
        keyboardTypeMap.put(getStartFirstCommand(), KeyboardType.QUEST_MENU);
        keyboardTypeMap.put(getStartFirstCardCommand(), KeyboardType.QUEST_MENU);
        keyboardTypeMap.put(getChangeCardCommand(), KeyboardType.QUEST_SHOP);
        keyboardTypeMap.put(getBuyUseHealCommand(), KeyboardType.QUEST_SHOP);
        keyboardTypeMap.put(getQuestMenuCommand(), KeyboardType.MENU);
        keyboardTypeMap.put(getQuestInstantHealCommand(), KeyboardType.QUEST_SHOP);
        keyboardTypeMap.put(getPVEMenuCommand(), KeyboardType.MENU);
        return keyboardTypeMap;
    }

    @Bean
    @Scope("singleton")
    public CommandFactory getCommandFactory(){
        commandMap.put("/register", getRegisterCommand());
        commandMap.put("/help", getHelpCommand());
        commandMap.put("/back", getBackCommand());
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
        commandMap.put("/achievements",  getAchievementsCommand());
        commandMap.put("/set_attack", getSetAttackCommand());
        commandMap.put("/set_defence",  getSetDefenceCommand());
        commandMap.put("/subscribe_battle", getBattleSubscribeCommand());
        commandMap.put("/unsubscribe_battle", getBattleUnsubscribeCommand());
        commandMap.put("/buy",  getBuyCommand());
        commandMap.put("/buy_card", getBuyCardCommand());
        commandMap.put("/list",  getListCommand());
        commandMap.put("/list_card", getListCardCommand());
        commandMap.put("/cancel", getCancelCommand());
        commandMap.put("/cancel_card", getCancelCardCommand());
        commandMap.put("/marketplace", getMarketplaceCommand());
        commandMap.put("/tasks", getTasksCommand());
        commandMap.put("/quest_resume", getContinueCommand());
        commandMap.put("/change", getChangeCommand());
        commandMap.put("/start_first", getStartFirstCommand());
        commandMap.put("/start_first_card", getStartFirstCardCommand());
        commandMap.put("/change_card", getChangeCardCommand());
        commandMap.put("/buy_use_heal", getBuyUseHealCommand());
        commandMap.put("/quest_menu", getQuestMenuCommand());
        commandMap.put("/quest_instant_heal", getQuestInstantHealCommand());
        commandMap.put("/pve_menu", getPVEMenuCommand());
        Map<String, Command> adminCommands = new HashMap<>();
        adminCommands.put("/add_tokens", getAddTokensCommand());
        adminCommands.put("/notify_all", getNotifyAllCommand());
        adminCommands.put("/user_stats", getUserStatsCommand());
        adminCommands.put("/all_users", getUsersStatsCommand());
        adminCommands.put("/admin", getAdminCommand());
        return new CommandFactoryImpl(commandMap, adminCommands);
    }

    @Bean
    @Scope("singleton")
    public BackCommand getBackCommand() {
        return new BackCommand();
    }

    @Bean
    @Scope("singleton")
    public PVEMenuCommand getPVEMenuCommand() {
        return new PVEMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public QuestInstantHealCommand getQuestInstantHealCommand() {
        return new QuestInstantHealCommand();
    }

    @Bean
    @Scope("singleton")
    public QuestMenuCommand getQuestMenuCommand() {
        return new QuestMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public ContinueQuestCommand getContinueCommand() {
        return new ContinueQuestCommand();
    }

    @Bean
    @Scope("singleton")
    public ChangeCommand getChangeCommand() {
        return new ChangeCommand ();
    }

    @Bean
    @Scope("singleton")
    public StartFirstQuestCommand getStartFirstCommand() {
        return new StartFirstQuestCommand();
    }

    @Bean
    @Scope("singleton")
    public StartFirstQuestCardCommand getStartFirstCardCommand() {
        return new StartFirstQuestCardCommand();
    }

    @Bean
    @Scope("singleton")
    public ChangeCardCommand getChangeCardCommand() {
        return new ChangeCardCommand();
    }

    @Bean
    @Scope("singleton")
    public BuyAndUseHealCommand getBuyUseHealCommand() {
        return new BuyAndUseHealCommand();
    }

    @Bean
    @Scope("singleton")
    public UserBalanceService userBalanceService() {
        return new UserBalanceServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public MyTasksCommand getTasksCommand(){
        return new MyTasksCommand();
    }

    @Bean
    @Scope("singleton")
    public OpenTaskBoxCommand openTaskBoxCommand(){
        return new OpenTaskBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public MarketplaceDAO marketplaceDAO(){
        return new PSQLMarketplaceDAO();
    }

    @Bean
    @Scope("singleton")
    public OccupationService occupationService(){
        return new OccupationServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public MarketplaceService marketplaceService(){
        return new MarketplaceServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public BuyCommand getBuyCommand(){
        return new BuyCommand();
    }

    @Bean
    @Scope("singleton")
    public BuyCardCommand getBuyCardCommand(){
        return new BuyCardCommand();
    }

    @Bean
    @Scope("singleton")
    public CancelCommand getCancelCommand(){
        return new CancelCommand();
    }

    @Bean
    @Scope("singleton")
    public CancelCardCommand getCancelCardCommand(){
        return new CancelCardCommand();
    }

    @Bean
    @Scope("singleton")
    public MarketplaceMenuCommand getMarketplaceCommand(){
        return new MarketplaceMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public ListCommand getListCommand(){
        return new ListCommand();
    }

    @Bean
    @Scope("singleton")
    public ListCardCommand getListCardCommand(){
        return new ListCardCommand();
    }

    @Bean
    @Scope("singleton")
    public BattleUnsubscribeCommand getBattleUnsubscribeCommand(){
        return new BattleUnsubscribeCommand();
    }

    @Bean
    @Scope("singleton")
    public BattleSubscribeCommand getBattleSubscribeCommand(){
        return new BattleSubscribeCommand();
    }

    @Bean
    @Scope("singleton")
    public NotificationPublisher notificationPublisher(){
        return new BattleNotificationPublisher();
    }

    @Bean
    @Scope("singleton")
    public Command getSetAttackCommand() {
        return new BattleSetAttackCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getSetDefenceCommand() {
        return new BattleSetDefenceCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getAdminCommand() {
        return new AdminMenuCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getAchievementsCommand() {
        return new AchievementStatsCommand();
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
    public HealCardCommand getHealCardCommand() {
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
    public UseHealCommand getUseHealCommand() {
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
    public BuyHealCommand getBuyHealCommand() {
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
        pathMap.put(new ImageIdentifier(CardName.TASK_1, CardType.LEGENDARY), MessageBundle.getSetting("TASK_1"));
        pathMap.put(new ImageIdentifier(CardName.TASK_2, CardType.LEGENDARY), MessageBundle.getSetting("TASK_2"));
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
    public AchievementService achievementService() {
        return new AchievementServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public AchievementDAO achievementDAO () {
        return new PSQLAchievementDAO();
    }

    @Bean
    @Scope("singleton")
    public AchievementChecker achievementChecker() {
        return new AchievementCheckerImpl();
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
    public BattleServiceImpl battleService() {
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
