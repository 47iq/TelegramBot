package util;

import game.entity.*;
import game.battle.AttackType;
import game.dungeon.Enemy;
import game.marketplace.Merchandise;
import game.quest.QuestState;
import game.quest.QuestType;

import java.util.List;

/**
 * Interface that provides message preparing methods
 */
public interface MessageFormatter {
    /**
     * Simple card message
     *
     * @param card card
     * @return card message
     */

    String getCardMessage(Card card);

    /**
     * Shop information for a concrete user
     *
     * @param user user
     * @return shop info
     */
    String getShopInfo(User user);

    /**
     * Stats for a concrete user
     *
     * @param user user
     * @return stats
     * @see command.stats.MyStatsCommand
     */

    String getUserStats(User user);

    String getLevelMessage(Card card);

    /**
     * Health info for card
     *
     * @param card card
     * @return health info
     * @see command.item.HealCardCommand
     */

    String getHealthMessage(Card card);

    /**
     * Price info for card
     *
     * @param x card
     * @return price info
     * @see command.card_collection.SellCardCommand
     */

    String getPriceMessage(Card x);

    String getPriceMessage(long x);

    String getShortMessage(Card x);

    /**
     * Battle turn info
     *
     * @param attackingCard attacking card
     * @param defendingCard defending card
     * @param damage        dealt  damage
     * @param health        remaining health of a defending card
     * @return battle turn info
     * @see game.service.BattleServiceImpl
     */

    String getBattleMessage(Card attackingCard, Card defendingCard, double damage, double health);

    String getBattleWinMessage(Card firstCard);

    /**
     * Battle start info
     *
     * @param firstUser  first user
     * @param firstCard  first card
     * @param secondUser second user
     * @param secondCard second card
     * @return battle start info
     * @see game.service.BattleServiceImpl
     */

    String getBattleStartMessage(User firstUser, Card firstCard, User secondUser, Card secondCard);

    /**
     * Battle finish message
     *
     * @param secondUser winner
     * @param firstUser  loser
     * @return battle finish message
     * @see game.service.BattleServiceImpl
     */

    String getWinLossMessage(User secondUser, User firstUser);

    /**
     * App stats info
     *
     * @param userList users
     * @param cardList cards
     * @return app stats
     * @see command.stats.AppStatsCommand
     */

    String getAppStats(List<User> userList, List<Card> cardList);

    /**
     * Top 10 of users by winrate
     *
     * @param userList users
     * @return top 10
     */

    String getGlobalStatsMessage(List<User> userList);

    /**
     * Loot cave message
     *
     * @param type   type of message
     * @param tokens gained tokens
     * @return loot cave message
     * @see game.dungeon.LootCave
     */

    String getLootCaveMessage(long type, long tokens);

    /**
     * Robbery cave message
     *
     * @param type   type of message
     * @param tokens spent tokens
     * @return robbery cave message
     * @see game.dungeon.RobberyCave
     */

    String getRobberyCaveMessage(long type, long tokens);

    /**
     * Trap cave message
     *
     * @param type       type of message
     * @param lostHealth lost health
     * @param card       card
     * @return trap cave message
     * @see game.dungeon.TrapCave
     */

    String getTrapCaveMessage(long type, long lostHealth, Card card);

    /**
     * Trap cave death message
     *
     * @param type       type of message
     * @param lostHealth lost health
     * @param card       card
     * @return trap cave death message
     * @see game.dungeon.TrapCave
     */

    String getTrapCaveDeadMessage(long type, long lostHealth, Card card);

    /**
     * Heal cave message
     *
     * @param type         type of message
     * @param gainedHealth gained health
     * @param card         card
     * @return heal cave message
     * @see game.dungeon.HealCave
     */

    String getHealCaveMessage(long type, long gainedHealth, Card card);

    /**
     * Weapon cave message
     *
     * @param type        type of message
     * @param attackBoost attack boost
     * @param card        card
     * @return weapon cave message
     * @see game.dungeon.WeaponCave
     */

    String getWeaponCaveMessage(long type, long attackBoost, Card card);

    /**
     * Armor cave message
     *
     * @param type       type of message
     * @param armorBoost armor boost
     * @param card       card
     * @return armor cave message
     */

    String getArmorCaveMessage(long type, long armorBoost, Card card);

    /**
     * Win message for PVE battle
     *
     * @param card  card
     * @param enemy enemy
     * @return win message
     * @see game.service.BattleServiceImpl
     */

    String getEnemyBattleWinMessage(Card card, Enemy enemy);

    /**
     * Lose message for PVE battle
     *
     * @param card  card
     * @param enemy enemy
     * @return lose message
     * @see game.service.BattleServiceImpl
     */

    String getEnemyBattleLoseMessage(Card card, Enemy enemy);

    /**
     * Battle start message for PVE battle
     *
     * @param card  card
     * @param enemy enemy
     * @return battle start message
     * @see game.service.BattleServiceImpl
     */

    String getEnemyBattleStartMessage(Enemy enemy, Card card);

    /**
     * Level up cave message
     *
     * @param l    type of message
     * @param card card
     * @return level up cave message
     * @see game.dungeon.LevelUpCave
     */

    String getLevelUpCaveMessage(long l, Card card);

    /**
     * Level up cave message if max level has been reached
     *
     * @param l    type of message
     * @param card card
     * @return level up cave message
     * @see game.dungeon.LevelUpCave
     */

    String getLevelUpCaveMaxLevelMessage(long l, Card card);

    /**
     * Card view short message
     *
     * @param x card
     * @return card view message
     */

    String getCardViewMessage(Card x);

    /**
     * Card view very short message
     *
     * @param x card
     * @return card view message
     * @see command.card_collection.SellCardCommand
     */

    String getCardViewMessage2(Card x);

    /**
     * Battle xp message
     *
     * @param winner   winner
     * @param gainedXp gained xp
     * @return battle xp message
     * @see game.service.BattleServiceImpl
     */

    String getBattleXpMessage(Card winner, long gainedXp);

    /**
     * Level up battle message
     *
     * @param winner winner
     * @return battle level up message
     * @see game.service.BattleServiceImpl
     */

    String getLevelUpMessage(Card winner);

    /**
     * Start shop message for a concrete user
     *
     * @param user user
     * @return start shop message
     * @see command.tutorial.StartShopCommand
     */

    String getStartShopInfo(User user);

    /**
     * Main menu help
     *
     * @return main menu help
     * @see command.main_menu.InfoCommand
     */

    String getMainMenuInfo();

    /**
     * Item menu help
     *
     * @return item menu help
     * @see command.item.ItemInfoCommand
     */

    String getItemInfo();

    /**
     * Dungeon menu help
     *
     * @return dungeon menu help
     * @see command.dungeon.DungeonInfoCommand
     */

    String getDungeonInfo();

    /**
     * Shop menu help
     *
     * @return shop menu help
     * @see command.shop.ShopInfoCommand
     */

    String getShopInfo();

    /**
     * Loot box cave message
     *
     * @param cardName special card name
     * @return loot box cave message
     * @see game.dungeon.LootBoxCave
     */

    String getLootBoxCaveMessage(CardName cardName);

    /**
     * All users message
     *
     * @param allUsers all users of app
     * @return users message
     */

    String getUsersStats(List<User> allUsers);

    /**
     * Instant heal message
     *
     * @param card card
     * @return instant heal message
     */

    String getInstantHealMessage(Card card);

    /**
     * Item list message
     *
     * @param user user
     * @return  user item list
     */

    String getItemMessage(User user);

    /**
     * Lootbox shop menu message
     *
     * @param user user
     * @return lootbox shop menu message
     */

    String getBuyLootboxInfo(User user);

    /**
     * Item shop menu message
     *
     * @param user user
     * @return item shop menu message
     */

    String getBuyItemInfo(User user);

    String getCaveAchievementMessage(User user);

    String getBoxCaveAchievementMessage(User user);

    String getBattleAchievementMessage(User user);

    String getCardsAchievementMessage(User user);

    String getUserAchievementsMessage(User user);

    String getLeaveMessage();

    String getAttackChoiceMessage();

    String getBattleHealMessage(Card attackCard, double value);

    String getBattleBlockMessage(Card attackCard, Card defenceCard, AttackType attackType);

    String getBattleMissMessage(Card attackCard, AttackType attackType);

    String getBattleHitMessage(Card attackCard, Card defenceCard, double hitPower, AttackType attackType);

    String getBattleCritMessage(AttackType attackType);

    String getDefenceChoiceMessage();

    String getBattleSelfKillMessage(Card attackCard);

    String getOpponentLeaveMessage();

    String getBattleNotifyMessage();

    String getBattleQueueTimeoutMessage();

    String getBattleWaitingMessage();

    String getMarketplaceTimeoutMessage();

    String getMarketplaceSoldMessage(Card card);

    String getMarketplaceCardsMessage(List<Merchandise> merchandises);

    String getTaskCompleteMessage(Task task);

    String getTaskStartMessage(Task task);

    String getAllTasksMessage(List<Task> all);

    String getAchievementMessage(User user, AchievementType achievementType);

    String getQuestShopMessage(QuestType questType, long step);

    String getQuestMessage(QuestType quest1, long step);

    String getQuestFinishMessage(QuestState type);
}
