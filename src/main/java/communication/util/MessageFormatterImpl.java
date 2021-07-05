package communication.util;

import data.CardService;
import data.UserService;
import game.Card;
import game.CardType;
import game.PriceCalculator;
import data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ResourceBundle;

@Component
public class MessageFormatterImpl implements MessageFormatter{

    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;
    @Autowired
    PriceCalculator priceCalculator;

    @Override
    public String getCardMessage(Card card) {
        return MessageBundle.getMessage(card.getType().name()) + " " + MessageBundle.getMessage(card.getName().name())
                + MessageBundle.getMessage("info_uid") + card.getUID() +") " + card.getLevel() +
                " " + MessageBundle.getMessage("info_level") + "\n" + MessageBundle.getMessage("info_health")
                + String.format("%.2f",card.getHealth()) + MessageBundle.getMessage("info_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("info_defence") + String.format("%.2f",card.getDefence());
    }

    @Override
    public String getShopInfo(User user) {
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        String  message = MessageBundle.getMessage("info_tokens") + " " + tokens + ", " +
                MessageBundle.getMessage("info_heal") + " " + heal + ", " +
                MessageBundle.getMessage("info_boost") + " " + boost + "\n" +
                MessageBundle.getMessage("info_prices") + "\n";
        message += MessageBundle.getMessage("info_heal1") + " " + settings.getString("HEAL_COST") + MessageBundle.getMessage("info_price2") + "\n";
        message += MessageBundle.getMessage("info_boost1") + " " + settings.getString("BOOST_COST") + MessageBundle.getMessage("info_price2") + "\n";
        message += MessageBundle.getMessage("info_boxes") + '\n';
        message += MessageBundle.getMessage("info_basic") + " " + settings.getString("BASIC_COST") + MessageBundle.getMessage("info_price2") + "\n";
        message += MessageBundle.getMessage("info_advanced") + " " + settings.getString("ADVANCED_COST") + MessageBundle.getMessage("info_price2") + "\n";
        message += MessageBundle.getMessage("info_pro") + " " + settings.getString("PRO_COST") + MessageBundle.getMessage("info_price2") + "\n";
        message += MessageBundle.getMessage("info_beer1") + "\n";
        return message;
    }

    @Override
    public String getUserStats(User user) {
        List<Card> cardList = cardService.getAllCardsOf(user);
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        long count = cardList.stream().map(Card::getName).distinct().count();
        String  message = MessageBundle.getMessage("info_tokens") + " " + tokens + "\n" +
                MessageBundle.getMessage("info_heal1") + " " + heal + "\n" +
                MessageBundle.getMessage("info_boost1") + " " + boost + "\n" +
                MessageBundle.getMessage("info_cards") + " " + cardList.size() + "\n" +
                MessageBundle.getMessage("info_basiccnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.BASIC)).count() + "\n" +
                MessageBundle.getMessage("info_rarecnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.RARE)).count() + "\n" +
                MessageBundle.getMessage("info_epiccnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.EPIC)).count() + "\n" +
                MessageBundle.getMessage("info_legcnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.LEGENDARY)).count() + "\n" +
                MessageBundle.getMessage("info_distinct") + " " + count + "/5" + "\n" +
                MessageBundle.getMessage("info_battlestats") + " " + user.getTotalWins() + " " + MessageBundle.getMessage("info_of") + " " + user.getTotalBattles() + "\n";
        if(user.getTotalBattles() != 0) {
            double a = user.getTotalWins();
            double b = user.getTotalBattles();
            message += MessageBundle.getMessage("info_winrate") + " " + String.format("%.1f", a/b*100) + "%\n";
        }
        //todo
        return message;
    }

    @Override
    public String getLevelMessage(Card card) {
        return MessageBundle.getMessage("info_boost2") + " " + card.getLevel();
    }

    @Override
    public String getHealthMessage(Card card) {
        return MessageBundle.getMessage("info_health2") + " " + String.format("%.1f",card.getHealth()) + "/" + String.format("%.1f",card.getMaxHealth());
    }

    @Override
    public String getPriceMessage(Card x) {
        return priceCalculator.calculatePrice(x) + MessageBundle.getMessage("info_price2");
    }

    @Override
    public String getShortMessage(Card x) {
        return MessageBundle.getMessage("info_health2") + String.format("%.1f",x.getHealth()) + " "
                + MessageBundle.getMessage("info_attack2") + String.format("%.1f",x.getAttack()) + " "
                + MessageBundle.getMessage("info_defence2") + String.format("%.1f",x.getDefence()) + " ";
    }

    @Override
    public String getBattleMessage(Card attackingCard, Card defendingCard, double damage, double health) {
        return MessageBundle.getMessage(attackingCard.getName().name() + "_short") + " (id:" + attackingCard.getUID() + ")  " +
                MessageBundle.getMessage("battle_deals") + " " + String.format("%.1f",damage) + MessageBundle.getMessage("battle_to") + "\n" +
                MessageBundle.getMessage(defendingCard.getName().name() + "_short") + " (id:" + defendingCard.getUID() + ") " +
                MessageBundle.getMessage("battle_nowhas") + " " + MessageBundle.getMessage("info_health2") + String.format("%.1f",health) + "\n";
    }

    @Override
    public String getBattleWinMessage(Card firstCard) {
        return MessageBundle.getMessage(firstCard.getName().name() + "_short") + " " + MessageBundle.getMessage("battle_wins") + "\n";
    }

    @Override
    public String getBattleStartMessage(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        return MessageBundle.getMessage("battle_start") + " " + firstUser.getUID() + " " +
                MessageBundle.getMessage("battle_card") + " " + getShortMessage(firstCard) + " " +
                MessageBundle.getMessage("battle_and") + " " + secondUser.getUID() + " " +
                        MessageBundle.getMessage("battle_card") + " " + getShortMessage(secondCard) + "\n";
    }

    @Override
    public String getWinLossMessage(User secondUser, User firstUser) {
        return secondUser.getUID() + MessageBundle.getMessage("battle_winner") + " " + firstUser.getUID() + MessageBundle.getMessage("battle_loser");
    }
}
