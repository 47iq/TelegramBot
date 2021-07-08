package communication.util;

import data.CardService;
import data.UserService;
import game.dungeon.Enemy;
import game.entity.Card;
import game.entity.CardName;
import game.entity.CardType;
import game.service.PriceCalculator;
import data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MessageFormatterImpl implements MessageFormatter {

    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;
    @Autowired
    PriceCalculator priceCalculator;

    @Override
    public String getCardMessage(Card card) {
        String s = MessageBundle.getMessage(card.getType().name()) + " " + MessageBundle.getMessage(card.getName().name())
                + MessageBundle.getMessage("info_uid") + card.getUID() + ") " + card.getLevel() +
                " " + MessageBundle.getMessage("info_level") + "\n" + MessageBundle.getMessage("info_health")
                + String.format("%.2f", card.getHealth()) + MessageBundle.getMessage("info_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("info_defence") + String.format("%.2f", card.getDefence()) + " ";
        if(card.getLevel() < 20)
            s += MessageBundle.getMessage("info_nextxp2") + " " + card.getNextLevelXp() + MessageBundle.getMessage("info_xp2");
        return  s;
    }

    @Override
    public String getShopInfo(User user) {
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        String message = MessageBundle.getMessage("info_tokens") + " " + tokens + ", " +
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
    public String getStartShopInfo(User user) {
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        return MessageBundle.getMessage("info_tokens") + " " + tokens + ", " +
                MessageBundle.getMessage("info_heal") + " " + heal + ", " +
                MessageBundle.getMessage("info_boost") + " " + boost + "\n\n" +
                MessageBundle.getMessage("info_startshop") + "\n\n" +
                MessageBundle.getMessage("info_basic") + " " + settings.getString("BASIC_COST") + MessageBundle.getMessage("info_price2") + "\n";
    }

    @Override
    public String getMainMenuInfo() {
        return MessageBundle.getMessage("info_menushop") + "\n" +
                MessageBundle.getMessage("info_menubattle") + "\n" +
                MessageBundle.getMessage("info_dungeon") + "\n" +
                MessageBundle.getMessage("info_mycollection") + "\n" +
                MessageBundle.getMessage("info_items") + "\n" +
                MessageBundle.getMessage("info_stats");
    }

    @Override
    public String getItemInfo() {
        return MessageBundle.getMessage("info_menuheal") + "\n" +
                MessageBundle.getMessage("info_menuboost");
    }

    @Override
    public String getDungeonInfo() {
        return MessageBundle.getMessage("info_dungeons");
    }

    @Override
    public String getShopInfo() {
        return MessageBundle.getMessage("info_basic2") + "\n" +
                MessageBundle.getMessage("info_advanced2") + "\n" +
                MessageBundle.getMessage("info_pro2") + "\n" +
                MessageBundle.getMessage("info_heal2") + "\n" +
                MessageBundle.getMessage("info_booster2") + "\n" +
                MessageBundle.getMessage("info_type") + "\n" +
                MessageBundle.getMessage("info_name") + "\n";
    }

    @Override
    public String getUserStats(User user) {
        List<Card> cardList = cardService.getAllCardsOf(user);
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        long count = cardList.stream().map(Card::getName).distinct().count();
        String message = MessageBundle.getMessage("info_tokens") + " " + tokens + "\n" +
                MessageBundle.getMessage("info_heal1") + " " + heal + "\n" +
                MessageBundle.getMessage("info_boost1") + " " + boost + "\n" +
                MessageBundle.getMessage("info_cards") + " " + cardList.size() + "\n" +
                MessageBundle.getMessage("info_basiccnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.BASIC)).count() + "\n" +
                MessageBundle.getMessage("info_rarecnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.RARE)).count() + "\n" +
                MessageBundle.getMessage("info_epiccnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.EPIC)).count() + "\n" +
                MessageBundle.getMessage("info_legcnt") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.LEGENDARY)).count() + "\n" +
                MessageBundle.getMessage("info_distinct") + " " + count + "/9" + "\n" +
                MessageBundle.getMessage("info_battlestats") + " " + user.getTotalWins() + " " + MessageBundle.getMessage("info_of") + " " + user.getTotalBattles() + "\n";
        if (user.getTotalBattles() != 0) {
            double a = user.getTotalWins();
            double b = user.getTotalBattles();
            message += MessageBundle.getMessage("info_winrate") + " " + String.format("%.1f", a / b * 100) + "%\n";
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
        return MessageBundle.getMessage("info_health2") + " " + String.format("%.1f", card.getHealth()) + "/" + String.format("%.1f", card.getMaxHealth());
    }

    @Override
    public String getPriceMessage(Card x) {
        return priceCalculator.calculatePrice(x) + MessageBundle.getMessage("info_price2");
    }

    @Override
    public String getShortMessage(Card x) {
        return MessageBundle.getMessage("info_health2") + String.format("%.1f", x.getHealth()) + " "
                + MessageBundle.getMessage("info_attack2") + String.format("%.1f", x.getAttack()) + " "
                + MessageBundle.getMessage("info_defence2") + String.format("%.1f", x.getDefence()) + " ";
    }

    @Override
    public String getBattleMessage(Card attackingCard, Card defendingCard, double damage, double health) {
        String message = "";
        if (attackingCard.getName() != null)
            message += MessageBundle.getMessage(attackingCard.getName().name() + "_short") + " (id:" + attackingCard.getUID() + ") ";
        else
            message += MessageBundle.getMessage("battle_enemy") + " ";
        message += MessageBundle.getMessage("battle_deals") + " " + String.format("%.1f", damage) + " " + MessageBundle.getMessage("battle_to") + "\n";
        if (defendingCard.getName() != null)
            message += MessageBundle.getMessage(defendingCard.getName().name() + "_short") + " (id:" + defendingCard.getUID() + ") ";
        else
            message += MessageBundle.getMessage("battle_enemy") + " ";
        message += MessageBundle.getMessage("battle_nowhas") + " " + MessageBundle.getMessage("info_health2") + String.format("%.1f", health) + "\n";
        return message;
    }

    @Override
    public String getBattleWinMessage(Card firstCard) {
        return MessageBundle.getMessage(firstCard.getName().name() + "_short") + " " + MessageBundle.getMessage("battle_wins") + "\n";
    }

    @Override
    public String getBattleStartMessage(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        return MessageBundle.getMessage("battle_start") + " " + firstUser.getUID() + " " +
                MessageBundle.getMessage("battle_card") + " \n" + getCardMessage(firstCard) + " \n" +
                MessageBundle.getMessage("battle_and") + " " + secondUser.getUID() + " " +
                MessageBundle.getMessage("battle_card") + " \n" + getCardMessage(secondCard) + "\n";
    }

    @Override
    public String getWinLossMessage(User secondUser, User firstUser) {
        return secondUser.getUID() + " " + MessageBundle.getMessage("battle_winner") + "\n "
                + firstUser.getUID() + " " + MessageBundle.getMessage("battle_loser");
    }

    @Override
    public String getAppStats(List<User> userList, List<Card> cardList) {
        StringBuilder builder = new StringBuilder();
        builder.append(MessageBundle.getMessage("stats_usercnt")).append(" ").append(userList.size()).append("\n");
        builder.append(MessageBundle.getMessage("stats_last24h")).append(" ").append(userList.stream().filter(x -> x.getLastTokensRedeemed()
                .plusHours(24).compareTo(LocalDateTime.now(ZoneId.systemDefault())) > 0).count()).append("\n");
        builder.append(MessageBundle.getMessage("info_cards")).append(" ").append(cardList.size()).append("\n");
        builder.append(MessageBundle.getMessage("info_basiccnt")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.BASIC)).count()).append("\n");
        builder.append(MessageBundle.getMessage("info_rarecnt")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.RARE)).count()).append("\n");
        builder.append(MessageBundle.getMessage("info_epiccnt")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.EPIC)).count()).append("\n");
        builder.append(MessageBundle.getMessage("info_legcnt")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.LEGENDARY)).count()).append("\n");
        builder.append(MessageBundle.getMessage("stats_battlescnt")).append(" ").append((userList.stream().map(User::getTotalBattles).reduce(Integer::sum)).get() / 2).append("\n");
        return builder.toString();
    }

    @Override
    public String getGlobalStatsMessage(List<User> userList) {
        List<User> top = userList.stream()
                .filter(x -> x.getTotalBattles() > 9)
                .sorted(Comparator.comparingDouble(x -> (((double) x.getTotalWins()) / ((double) x.getTotalBattles()))))
                .limit(10)
                .collect(Collectors.toList());
        StringBuilder topStr = new StringBuilder();
        int top1 = 0;
        for (int i = Math.min(10, top.size()) - 1; i >= 0; i--) {
            top1++;
            topStr.append(String.valueOf(top1)).append(") @").append(top.get(i).getUID()).append(" ")
                    .append(top.get(i).getTotalWins()).append(" ").append(MessageBundle.getMessage("info_of")).append(" ").append(top.get(i).getTotalBattles()).append("\n");
        }
        return topStr.toString();
    }

    @Override
    public String getLootCaveMessage(long type, long tokens) {
        return MessageBundle.getMessage("dungeon_lootcave_" + type) + " " + tokens + MessageBundle.getMessage("info_price2");
    }

    @Override
    public String getRobberyCaveMessage(long type, long tokens) {
        return MessageBundle.getMessage("dungeon_robberycave_" + type) + " " + tokens + MessageBundle.getMessage("info_price2");
    }

    @Override
    public String getTrapCaveMessage(long type, long lostHealth, Card card) {
        return MessageBundle.getMessage("dungeon_trapcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) + " " + MessageBundle.getMessage("info_loses") + " " + lostHealth + MessageBundle.getMessage("info_health2");
    }

    @Override
    public String getTrapCaveDeadMessage(long type, long lostHealth, Card card) {
        return MessageBundle.getMessage("dungeon_trapcave_" + type) + " " + MessageBundle.getMessage(card.getName().name())
                + " " + MessageBundle.getMessage("info_loses") + " " + lostHealth + MessageBundle.getMessage("info_health2") +
                " " + MessageBundle.getMessage("dungeon_trapdead_" + type);
    }

    @Override
    public String getHealCaveMessage(long type, long gainedHealth, Card card) {
        return MessageBundle.getMessage("dungeon_healcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_heals") + " " + gainedHealth + MessageBundle.getMessage("info_health2");
    }

    @Override
    public String getWeaponCaveMessage(long type, long attackBoost, Card card) {
        return MessageBundle.getMessage("dungeon_weaponcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_attackboost") + " " + attackBoost + MessageBundle.getMessage("info_attack2");
    }

    @Override
    public String getArmorCaveMessage(long type, long armorBoost, Card card) {
        return MessageBundle.getMessage("dungeon_armorcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_armorboost") + " " + armorBoost + MessageBundle.getMessage("info_defence2");
    }

    @Override
    public String getLevelUpCaveMessage(long l, Card card) {
        //todo
        return MessageBundle.getMessage("dungeon_levelupcave_0") + MessageBundle.getMessage("info_level2");

    }

    @Override
    public String getLevelUpCaveMaxLevelMessage(long l, Card card) {
        //todo
        return MessageBundle.getMessage("dungeon_nolevelupcave_0");
    }

    @Override
    public String getLootBoxCaveMessage(CardName cardName) {
        System.out.println(cardName.name() + "_dropmsg");
        //todo
        return MessageBundle.getMessage(cardName.name() + "_dropmsg");
    }

    @Override
    public String getEnemyBattleWinMessage(Card card, Enemy enemy) {
        return MessageBundle.getMessage(card.getName().name()) + " " + MessageBundle.getMessage("dungeon_win") + " " + MessageBundle.getMessage(enemy.getEnemyType().name())
                + " " + MessageBundle.getMessage("dungeon_getaward") + " " + enemy.getAward() + MessageBundle.getMessage("info_price2") + "\n\n" + MessageBundle.getMessage(enemy.getEnemyType().name() + "_winhook");
    }

    @Override
    public String getEnemyBattleLoseMessage(Card card, Enemy enemy) {
        return MessageBundle.getMessage(enemy.getEnemyType().name() + "_name") + " " + MessageBundle.getMessage("dungeon_win") + " " + MessageBundle.getMessage(card.getName().name()) + "\n\n" + MessageBundle.getMessage(enemy.getEnemyType().name() + "_losehook")
                + "\n" + MessageBundle.getMessage("dungeon_gohome");
    }

    @Override
    public String getEnemyBattleStartMessage(Enemy enemy, Card card) {
        return MessageBundle.getMessage("battle_start") + "\n" + getCardMessage2(card) + " \n" +
                MessageBundle.getMessage("battle_and") + " " + getEnemyMessage(enemy) + "\n";
    }

    @Override
    public String getCardViewMessage(Card x) {
        return MessageBundle.getMessage(x.getType().name() + "_2") + " " + MessageBundle.getMessage(x.getName().name() + "_short") + " (id:" + x.getUID() + ")";
    }

    @Override
    public String getCardViewMessage2(Card x) {
        return MessageBundle.getMessage(x.getType().name() + "_2") + " (id:" + x.getUID() + ")";
    }

    @Override
    public String getBattleXpMessage(Card winner, long gainedXp) {
        if (winner.getLevel() < Long.parseLong(ResourceBundle.getBundle("settings").getString("MAX_LEVEL")))
            return getCardMessage2(winner) + " " + MessageBundle.getMessage("info_gainsxp")
                    + " " + gainedXp + MessageBundle.getMessage("info_xp2") + "\n" +
                    MessageBundle.getMessage("info_nextxp") + " " + winner.getNextLevelXp() + MessageBundle.getMessage("info_xp2");
        else
            return "";
    }

    @Override
    public String getLevelUpMessage(Card winner) {
        return getCardMessage2(winner) + " " + MessageBundle.getMessage("info_gainsnewlevel") + " " + winner.getLevel()
                + MessageBundle.getMessage("info_level2") + "\n" +
                MessageBundle.getMessage("info_nextxp") + " " + winner.getNextLevelXp() + MessageBundle.getMessage("info_xp2");
    }

    private String getCardMessage2(Card card) {
        return MessageBundle.getMessage(card.getName().name()) + ": " + MessageBundle.getMessage("info_health")
                + String.format("%.2f", card.getHealth()) + MessageBundle.getMessage("info_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("info_defence") + String.format("%.2f", card.getDefence());
    }

    private String getEnemyMessage(Enemy enemy) {
        return MessageBundle.getMessage(enemy.getEnemyType().name() + "_name") + " " + MessageBundle.getMessage("info_health")
                + String.format("%.2f", enemy.getEnemyCard().getHealth()) + MessageBundle.getMessage("info_attack") + String.format("%.2f", enemy.getEnemyCard().getAttack()) +
                MessageBundle.getMessage("info_defence") + String.format("%.2f", enemy.getEnemyCard().getDefence());
    }
}
