package communication.util;

import game.dungeon.Enemy;
import game.entity.Card;
import data.User;
import game.entity.CardName;

import java.util.List;

public interface MessageFormatter {
    String getCardMessage(Card card);
    String getShopInfo(User user);
    String getUserStats(User user);
    String getLevelMessage(Card card);
    String getHealthMessage(Card card);
    String getPriceMessage(Card x);
    String getShortMessage(Card x);
    String getBattleMessage(Card attackingCard, Card defendingCard, double damage, double health);
    String getBattleWinMessage(Card firstCard);
    String getBattleStartMessage(User firstUser, Card firstCard, User secondUser, Card secondCard);
    String getWinLossMessage(User secondUser, User firstUser);
    String getAppStats(List<User> userList, List<Card> cardList);
    String getGlobalStatsMessage(List<User> userList);
    String getLootCaveMessage(long type, long tokens);
    String getRobberyCaveMessage(long type, long tokens);
    String getTrapCaveMessage(long type, long lostHealth, Card card);
    String getTrapCaveDeadMessage(long type, long lostHealth, Card card);
    String getHealCaveMessage(long type, long gainedHealth, Card card);
    String getWeaponCaveMessage(long type, long attackBoost, Card card);
    String getArmorCaveMessage(long type, long armorBoost, Card card);
    String getEnemyBattleWinMessage(Card card, Enemy enemy);
    String getEnemyBattleLoseMessage(Card card, Enemy enemy);
    String getEnemyBattleStartMessage(Enemy enemy, Card card);
    String getLevelUpCaveMessage(long l, Card card);
    String getLevelUpCaveMaxLevelMessage(long l, Card card);
    String getCardViewMessage(Card x);
    String getCardViewMessage2(Card x);
    String getBattleXpMessage(Card winner, long gainedXp);
    String getLevelUpMessage(Card winner);
    String getStartShopInfo(User user);
    String getMainMenuInfo();
    String getItemInfo();
    String getDungeonInfo();
    String getShopInfo();
    String getLootBoxCaveMessage(CardName cardName);
}
