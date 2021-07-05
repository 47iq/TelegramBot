package communication.util;

import game.Card;
import data.User;

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
}
