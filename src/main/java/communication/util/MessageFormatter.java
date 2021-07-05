package communication.util;

import game.Card;
import data.User;

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
}
