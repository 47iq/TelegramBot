package util;

import game.entity.*;
import game.quest.QuestState;
import game.quest.QuestType;
import game.service.*;
import game.battle.AttackType;
import game.dungeon.Enemy;
import game.marketplace.Merchandise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class MessageFormatterImpl implements MessageFormatter {

    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;
    @Autowired
    PriceCalculator priceCalculator;
    @Autowired
    AchievementService achievementService;

    @Override
    public String getCardMessage(Card card) {
        String s = MessageBundle.getMessage(card.getType().name()) + " " + MessageBundle.getMessage(card.getName().name())
                + MessageBundle.getMessage("card_message_uid") + card.getUID() + ") " + card.getLevel() +
                " " + MessageBundle.getMessage("card_message_level") + "\n" + MessageBundle.getMessage("card_message_health")
                + String.format("%.2f", card.getHealth()) + "/"+ String.format("%.2f", card.getMaxHealth()) + MessageBundle.getMessage("card_message_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("card_message_defence") + String.format("%.2f", card.getDefence()) + " ";
        if(card.getLevel() < 20)
            s += MessageBundle.getMessage("info_nextxp2") + " " + card.getNextLevelXp() + MessageBundle.getMessage("symbol_xp");
        return  s;
    }

    @Override
    public String getShopInfo(User user) {
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        return MessageBundle.getMessage("shop_tokens") + " " + tokens + ",\n" +
                MessageBundle.getMessage("shop_heal") + " " + heal + ",\n" +
                MessageBundle.getMessage("shop_boost") + " " + boost + "\n";
    }

    @Override
    public String getStartShopInfo(User user) {
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        return MessageBundle.getMessage("shop_tokens") + " " + tokens + ", " +
                MessageBundle.getMessage("shop_heal") + " " + heal + ", " +
                MessageBundle.getMessage("shop_boost") + " " + boost + "\n\n" +
                MessageBundle.getMessage("start_shop") + "\n\n" +
                MessageBundle.getMessage("shop_basic") + " " + MessageBundle.getSetting("BASIC_COST") + MessageBundle.getMessage("symbol_token") + "\n";
    }

    @Override
    public String getMainMenuInfo() {
        return MessageBundle.getMessage("help_shop") + "\n" +
                MessageBundle.getMessage("help_battle") + "\n" +
                MessageBundle.getMessage("info_dungeon") + "\n" +
                MessageBundle.getMessage("help_my.collection") + "\n" +
                MessageBundle.getMessage("help_items") + "\n" +
                MessageBundle.getMessage("help_stats");
    }

    @Override
    public String getItemInfo() {
        return MessageBundle.getMessage("help_menuheal") + "\n" +
                MessageBundle.getMessage("help_menuboost");
    }

    @Override
    public String getDungeonInfo() {
        return MessageBundle.getMessage("info_dungeons");
    }

    @Override
    public String getShopInfo() {
        return MessageBundle.getMessage("help_basic2") + "\n" +
                MessageBundle.getMessage("help_advanced2") + "\n" +
                MessageBundle.getMessage("help_pro2") + "\n" +
                MessageBundle.getMessage("help_heal2") + "\n" +
                MessageBundle.getMessage("help_boost2") + "\n" +
                MessageBundle.getMessage("help_type") + "\n" +
                MessageBundle.getMessage("help_name") + "\n";
    }

    @Override
    public String getUserStats(User user) {
        List<Card> cardList = cardService.getAllCardsOf(user);
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        long count = cardList.stream().map(Card::getName).distinct().count();
        String message = MessageBundle.getMessage("shop_tokens") + " " + tokens + "\n" +
                MessageBundle.getMessage("shop_heal1") + " " + heal + "\n" +
                MessageBundle.getMessage("shop_boost1") + " " + boost + "\n" +
                MessageBundle.getMessage("stats_cards") + " " + cardList.size() + "\n" +
                MessageBundle.getMessage("stats_basic") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.BASIC)).count() + "\n" +
                MessageBundle.getMessage("stats_rare") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.RARE)).count() + "\n" +
                MessageBundle.getMessage("stats_epic") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.EPIC)).count() + "\n" +
                MessageBundle.getMessage("stats_leg") + " " + cardList.stream().filter(x -> x.getType().equals(CardType.LEGENDARY)).count() + "\n" +
                MessageBundle.getMessage("stats_distinct") + " " + count + "/" +  CardName.values().length + "\n" +
                MessageBundle.getMessage("stats_battles") + " " + user.getTotalWins() + " " + MessageBundle.getMessage("stats_wins.of") + " " + user.getTotalBattles() + "\n";
        if (user.getTotalBattles() != 0) {
            double a = user.getTotalWins();
            double b = user.getTotalBattles();
            message += MessageBundle.getMessage("stats_win.rate") + " " + String.format("%.1f", a / b * 100) + "%\n";
        }
        return message;
    }

    @Override
    public String getLevelMessage(Card card) {
        return MessageBundle.getMessage("symbol_boost") + " " + card.getLevel();
    }

    @Override
    public String getHealthMessage(Card card) {
        return MessageBundle.getMessage("symbol_health") + " " + String.format("%.1f", card.getHealth()) + "/" + String.format("%.1f", card.getMaxHealth());
    }

    @Override
    public String getPriceMessage(Card x) {
        return priceCalculator.calculatePrice(x) + MessageBundle.getMessage("symbol_token");
    }

    @Override
    public String getShortMessage(Card x) {
        return MessageBundle.getMessage("symbol_health") + String.format("%.1f", x.getHealth()) + " "
                + MessageBundle.getMessage("symbol_attack") + String.format("%.1f", x.getAttack()) + " "
                + MessageBundle.getMessage("symbol_defence") + String.format("%.1f", x.getDefence()) + " ";
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
        message += MessageBundle.getMessage("battle_nowhas") + " " + MessageBundle.getMessage("symbol_health") + String.format("%.1f", health) + "\n";
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
        builder.append(MessageBundle.getMessage("stats_user.cnt"))
                .append(" ")
                .append(userList.size())
                .append("\n")
                .append(MessageBundle.getMessage("stats_last24h"))
                .append(" ")
                .append(userList.stream()
                .filter(x -> x.getLastVisited() != null)
                .filter(x -> x.getLastVisited()
                        .plusHours(24)
                        .compareTo(LocalDateTime.now(ZoneId.systemDefault())) > 0)
                        .count())
                .append("\n");
        builder.append(MessageBundle.getMessage("stats_cards")).append(" ").append(cardList.size()).append("\n");
        builder.append(MessageBundle.getMessage("stats_basic")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.BASIC)).count()).append("\n");
        builder.append(MessageBundle.getMessage("stats_rare")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.RARE)).count()).append("\n");
        builder.append(MessageBundle.getMessage("stats_epic")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.EPIC)).count()).append("\n");
        builder.append(MessageBundle.getMessage("stats_leg")).append(" ").append(cardList.stream().filter(x -> x.getType().equals(CardType.LEGENDARY)).count()).append("\n");
        builder.append(MessageBundle.getMessage("stats_battles.cnt")).append(" ").append((userList.stream().map(User::getTotalBattles).reduce(Integer::sum)).get() / 2).append("\n");
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
                    .append(top.get(i).getTotalWins()).append(" ").append(MessageBundle.getMessage("stats_wins.of")).append(" ").append(top.get(i).getTotalBattles()).append("\n");
        }
        if(topStr.toString().isEmpty())
            return MessageBundle.getMessage("stats_empty.top");
        return topStr.toString();
    }

    @Override
    public String getLootCaveMessage(long type, long tokens) {
        return MessageBundle.getMessage("dungeon_lootcave_" + type) + " " + tokens + MessageBundle.getMessage("symbol_token");
    }

    @Override
    public String getRobberyCaveMessage(long type, long tokens) {
        return MessageBundle.getMessage("dungeon_robberycave_" + type) + " " + tokens + MessageBundle.getMessage("symbol_token");
    }

    @Override
    public String getTrapCaveMessage(long type, long lostHealth, Card card) {
        return MessageBundle.getMessage("dungeon_trapcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) + " " + MessageBundle.getMessage("info_loses") + " " + lostHealth + MessageBundle.getMessage("symbol_health");
    }

    @Override
    public String getTrapCaveDeadMessage(long type, long lostHealth, Card card) {
        return MessageBundle.getMessage("dungeon_trapcave_" + type) + " " + MessageBundle.getMessage(card.getName().name())
                + " " + MessageBundle.getMessage("info_loses") + " " + lostHealth + MessageBundle.getMessage("symbol_health") +
                " " + MessageBundle.getMessage("dungeon_trapdead_" + type);
    }

    @Override
    public String getHealCaveMessage(long type, long gainedHealth, Card card) {
        return MessageBundle.getMessage("dungeon_healcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_heals") + " " + gainedHealth + MessageBundle.getMessage("symbol_health");
    }

    @Override
    public String getWeaponCaveMessage(long type, long attackBoost, Card card) {
        return MessageBundle.getMessage("dungeon_weaponcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_attackboost") + " " + attackBoost + MessageBundle.getMessage("symbol_attack");
    }

    @Override
    public String getArmorCaveMessage(long type, long armorBoost, Card card) {
        return MessageBundle.getMessage("dungeon_armorcave_" + type) + " " + MessageBundle.getMessage(card.getName().name()) +
                " " + MessageBundle.getMessage("info_armorboost") + " " + armorBoost + MessageBundle.getMessage("symbol_defence");
    }

    @Override
    public String getLevelUpCaveMessage(long l, Card card) {
        return MessageBundle.getMessage("dungeon_levelupcave_0") + MessageBundle.getMessage("symbol_level");

    }

    @Override
    public String getLevelUpCaveMaxLevelMessage(long l, Card card) {
        return MessageBundle.getMessage("dungeon_nolevelupcave_0");
    }

    @Override
    public String getLootBoxCaveMessage(CardName cardName) {
        return MessageBundle.getMessage(cardName.name() + "_dropmsg");
    }

    @Override
    public String getEnemyBattleWinMessage(Card card, Enemy enemy) {
        return MessageBundle.getMessage(card.getName().name()) + " " + MessageBundle.getMessage("dungeon_win") + " " + MessageBundle.getMessage(enemy.getEnemyType().name())
                + " " + MessageBundle.getMessage("dungeon_getaward") + " " + enemy.getAward() + MessageBundle.getMessage("symbol_token") + "\n\n" + MessageBundle.getMessage(enemy.getEnemyType().name() + "_winhook");
    }

    @Override
    public String getEnemyBattleLoseMessage(Card card, Enemy enemy) {
        return MessageBundle.getMessage(enemy.getEnemyType().name() + "_name") + " " + MessageBundle.getMessage("dungeon_win") + " " + MessageBundle.getMessage(card.getName().name()) + "\n\n" + MessageBundle.getMessage(enemy.getEnemyType().name() + "_losehook")
                + "\n" + MessageBundle.getMessage("dungeon_gohome");
    }

    @Override
    public String getQuestBattleLoseMessage(Card card, Enemy enemy) {
        return MessageBundle.getMessage(enemy.getEnemyType().name() + "_name") + " " + MessageBundle.getMessage("dungeon_win") + " " + MessageBundle.getMessage(card.getName().name()) + "\n\n" + MessageBundle.getMessage(enemy.getEnemyType().name() + "_losehook");
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
        if (winner.getLevel() < Long.parseLong(MessageBundle.getSetting("MAX_LEVEL")))
            return getCardMessage2(winner) + " " + MessageBundle.getMessage("info_gainsxp")
                    + " " + gainedXp + MessageBundle.getMessage("symbol_xp") + "\n" +
                    MessageBundle.getMessage("info_nextxp") + " " + winner.getNextLevelXp() + MessageBundle.getMessage("symbol_xp");
        else
            return "";
    }

    @Override
    public String getLevelUpMessage(Card winner) {
        return getCardMessage2(winner) + " " + MessageBundle.getMessage("info_gainsnewlevel") + " " + winner.getLevel()
                + MessageBundle.getMessage("symbol_level") + "\n" +
                MessageBundle.getMessage("info_nextxp") + " " + winner.getNextLevelXp() + MessageBundle.getMessage("symbol_xp");
    }

    @Override
    public String getUsersStats(List<User> allUsers) {
        return allUsers.stream()
                .map(x -> "@" + x.getUID() + " " + (x.getLastVisited() == null ? MessageBundle.getMessage("recently") : x.getLastVisited().format(DateTimeFormatter.ISO_DATE)))
                .reduce((x, y) -> x + "\n" + y)
                .orElse("");
    }

    @Override
    public String getInstantHealMessage(Card card) {
        return MessageBundle.getMessage("success.heal") + " " + MessageBundle.getMessage(card.getName().name()) + ": " + MessageBundle.getMessage("symbol_health") +
                " " + String.format("%.1f", card.getHealth()) + "/" + String.format("%.1f", card.getMaxHealth());
    }

    @Override
    public String getItemMessage(User user) {
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        return MessageBundle.getMessage("shop_heal").substring(0, 1).toUpperCase(Locale.ROOT) + MessageBundle.getMessage("shop_heal").substring(1) + " " + heal + ", " +
                MessageBundle.getMessage("shop_boost") + " " + boost;
    }

    @Override
    public String getBuyLootboxInfo(User user) {
        long tokens = userService.getBalance(user);
        String message = MessageBundle.getMessage("shop_tokens") + " " + tokens + "\n" +
                MessageBundle.getMessage("shop_prices") + "\n";
        message = addLootBoxInfo(message);
        return message;
    }

    private String addLootBoxInfo(String message) {
        message += MessageBundle.getMessage("shop_basic") + " " + MessageBundle.getSetting("BASIC_COST") + MessageBundle.getMessage("symbol_token") + "\n";
        message += MessageBundle.getMessage("shop_advanced") + " " + MessageBundle.getSetting("ADVANCED_COST") + MessageBundle.getMessage("symbol_token") + "\n";
        message += MessageBundle.getMessage("shop_pro") + " " + MessageBundle.getSetting("PRO_COST") + MessageBundle.getMessage("symbol_token") + "\n";
        return message;
    }

    @Override
    public String getBuyItemInfo(User user) {
        long tokens = userService.getBalance(user);
        long heal = user.getHealCount();
        long boost = user.getBoostCount();
        String message = MessageBundle.getMessage("shop_tokens") + " " + tokens + ", " +
                MessageBundle.getMessage("shop_heal") + " " + heal + ", " +
                MessageBundle.getMessage("shop_boost") + " " + boost + "\n" +
                MessageBundle.getMessage("shop_prices") + "\n";
        message += MessageBundle.getMessage("shop_heal1") + " " + MessageBundle.getSetting("HEAL_COST") + MessageBundle.getMessage("symbol_token") + "\n";
        message += MessageBundle.getMessage("shop_boost1") + " " + MessageBundle.getSetting("BOOST_COST") + MessageBundle.getMessage("symbol_token") + "\n";
        message += MessageBundle.getMessage("shop_beer") + "\n";
        return message;
    }

    private String getCardMessage2(Card card) {
        return MessageBundle.getMessage(card.getName().name()) + ": " + MessageBundle.getMessage("card_message_health")
                + String.format("%.2f", card.getHealth()) + MessageBundle.getMessage("card_message_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("card_message_defence") + String.format("%.2f", card.getDefence());
    }

    private String getEnemyMessage(Enemy enemy) {
        return MessageBundle.getMessage(enemy.getEnemyType().name() + "_name") + " " + MessageBundle.getMessage("card_message_health")
                + String.format("%.2f", enemy.getEnemyCard().getHealth()) + MessageBundle.getMessage("card_message_attack") + String.format("%.2f", enemy.getEnemyCard().getAttack()) +
                MessageBundle.getMessage("card_message_defence") + String.format("%.2f", enemy.getEnemyCard().getDefence());
    }

    @Override
    public String getCaveAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_caves") +  " "  + achievementService.getUsersAchievements(user).getCavesNumber();
    }

    @Override
    public String getBoxCaveAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_loot_boxes") +  " "  + achievementService.getUsersAchievements(user).getBoxCavesNumber();
    }

    @Override
    public String getBattleAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_battles") +  " "  + achievementService.getUsersAchievements(user).getBattlesNumber();
    }

    @Override
    public String getCardsAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_cards") +  " "  + achievementService.getUsersAchievements(user).getTotalCards();
    }

    @Override
    public String getUserAchievementsMessage(User user) {
        StringBuilder completed = new StringBuilder();
        StringBuilder active = new StringBuilder();
        Arrays.stream(AchievementType.values()).forEach(achievementType -> {
            long progress = achievementService.getProgress(user, achievementType);
            long needed = Long.parseLong(MessageBundle.getSetting( achievementType.name() + "_ACHIEVEMENT"));
            String beginning = MessageBundle.getMessage(achievementType.name().toLowerCase() + "_achievement");
            String ending = MessageBundle.getMessage(achievementType.name().toLowerCase() + "_achievement_ending");
            if(progress >= needed)
                completed.append(beginning).append(" ").append(MessageBundle.getMessage("completed")).append("\n\n");
            else
                active.append(beginning).append(" ").append(progress).append("/").append(needed).append(" ").append(ending).append("\n\n");
        });
        return completed.append(active).toString();
    }

    @Override
    public String getLeaveMessage() {
        return MessageBundle.getMessage("pvp_leave");
    }

    @Override
    public String getOpponentLeaveMessage() {
        return MessageBundle.getMessage("pvp_opponent.leave");
    }

    @Override
    public String getAttackChoiceMessage() {
        return MessageBundle.getMessage("info_attack.choice");
    }

    @Override
    public String getDefenceChoiceMessage() {
        return MessageBundle.getMessage("info_defence.choice");
    }

    @Override
    public String getBattleSelfKillMessage(Card attackCard) {
        return MessageBundle.getMessage(attackCard.getName().name() + "_short") + " (id:" + attackCard.getUID() + ") " + " " + MessageBundle.getMessage("pvp_self.kill");
    }

    @Override
    public String getBattleHealMessage(Card attackCard, double value) {
        return MessageBundle.getMessage(attackCard.getName().name() + "_short") + " (id:" + attackCard.getUID() + ") " + " " + MessageBundle.getMessage("info_heals")
                + " " + String.format("%.1f", value) + " " + MessageBundle.getMessage("symbol_health") + "\n"
                + MessageBundle.getMessage(attackCard.getName().name()) + " " + MessageBundle.getMessage("battle_nowhas")
                + " " + String.format("%.1f", attackCard.getHealth());
    }

    @Override
    public String getBattleBlockMessage(Card attackCard, Card defenceCard, AttackType attackType) {
        return MessageBundle.getMessage(defenceCard.getName().name() + "_short") + " (id:" + defenceCard.getUID() + ") " + " " + MessageBundle.getMessage("info_blocks")
                + " " + MessageBundle.getMessage(attackCard.getName().name() + "_short") + " (id:" + attackCard.getUID() + ") ";
    }

    @Override
    public String getBattleMissMessage(Card attackCard, AttackType attackType) {
        return MessageBundle.getMessage(attackCard.getName().name() + "_short") + " (id:" + attackCard.getUID() + ") " + " " + MessageBundle.getMessage("info_misses")
                + " " + MessageBundle.getMessage("miss_attack." + attackType.name().toLowerCase());
    }

    @Override
    public String getBattleHitMessage(Card attackCard, Card defenceCard, double hitPower, AttackType attackType) {
        String message = "";
        if (attackCard.getName() != null)
            message += MessageBundle.getMessage(attackCard.getName().name() + "_short") + " (id:" + attackCard.getUID() + ") ";
        else
            message += MessageBundle.getMessage("battle_enemy") + " ";
        message += MessageBundle.getMessage("battle_deals") + " " + String.format("%.1f", hitPower) + " " + MessageBundle.getMessage("damage." + attackType.name().toLowerCase()) + "\n";
        if (defenceCard.getName() != null)
            message += MessageBundle.getMessage(defenceCard.getName().name() + "_short") + " (id:" + defenceCard.getUID() + ") ";
        else
            message += MessageBundle.getMessage("battle_enemy") + " ";
        message += MessageBundle.getMessage("battle_nowhas") + " " + MessageBundle.getMessage("symbol_health") + String.format("%.1f", defenceCard.getHealth()) + "\n";
        return message;
    }

    @Override
    public String getBattleCritMessage(AttackType attackType) {
        return MessageBundle.getMessage("battle_crit");
    }

    @Override
    public String getBattleNotifyMessage() {
        return MessageBundle.getMessage("pvp_notify");
    }

    @Override
    public String getBattleQueueTimeoutMessage() {
        return MessageBundle.getMessage("pvp_queue.timeout");
    }

    @Override
    public String getBattleWaitingMessage() {
        return MessageBundle.getMessage("pvp_battle.waiting");
    }

    @Override
    public String getPriceMessage(long x) {
        return x + MessageBundle.getMessage("symbol_token");
    }

    @Override
    public String getMarketplaceTimeoutMessage() {
        return MessageBundle.getMessage("marketplace_timeout");
    }

    @Override
    public String getMarketplaceSoldMessage(Card card) {
        return MessageBundle.getMessage("marketplace_sold") + " " + getCardViewMessage(card);
    }

    @Override
    public String getMarketplaceCardsMessage(List<Merchandise> merchandises) {
        StringBuilder builder = new StringBuilder();
        AtomicInteger cnt = new AtomicInteger(1);
        merchandises.forEach(x -> {
            builder.append(cnt.get()).append(") @")
                    .append(cardService.getById(x.getCardUID()).getOwner())
                    .append("\n")
                    .append(getCardMessage(cardService.getById(x.getCardUID())))
                    .append("\n")
                    .append(MessageBundle.getMessage("marketplace_price"))
                    .append("  ")
                    .append(x.getCost())
                    .append(MessageBundle.getMessage("symbol_token"))
                    .append("\n\n");
            cnt.getAndIncrement();
        });
        return builder.toString();
    }

    @Override
    public String getTaskCompleteMessage(Task task) {
        String result = MessageBundle.getMessage("task.completed") + "\n\n";
        result += MessageBundle.getMessage("reward." + task.getRewardType().name().toLowerCase()) + " " + task.getReward();
        return result;
    }

    @Override
    public String getTaskStartMessage(Task task) {
        String result = MessageBundle.getMessage("task.begin") + "\n\n";
        result += MessageBundle.getMessage("task." +task.getTaskType().name().toLowerCase()) + " " + task.getProgress() + "/" + task.getNeeded() + "\n\n";
        result += MessageBundle.getMessage("reward." + task.getRewardType().name().toLowerCase()) + " " + task.getReward();
        return result;
    }

    @Override
    public String getAllTasksMessage(List<Task> all) {
        StringBuilder builder = new StringBuilder();
        all.forEach(task -> builder.append(MessageBundle.getMessage("task." + task.getTaskType().name().toLowerCase()))
                .append(" ")
                .append(task.getProgress())
                .append("/")
                .append(task.getNeeded())
                .append("\n")
                .append(MessageBundle.getMessage("reward." + task.getRewardType().name().toLowerCase()))
                .append(" ")
                .append(task.getReward())
                .append("\n\n"));
        return builder.toString();
    }

    @Override
    public String getAchievementMessage(User user, AchievementType achievementType) {
        return switch (achievementType) {
            case BATTLES -> getBattleAchievementMessage(user);
            case BOX_CAVES -> getBoxCaveAchievementMessage(user);
            case CAVES -> getCaveAchievementMessage(user);
            case CARDS -> getCardsAchievementMessage(user);
            case MONEY_SPENT -> getMoneySpentAchievementMessage(user);
            case TASKS -> getTasksAchievementMessage(user);
            case CARD_LEVEL -> getCardLevelAchievement(user);
            case PVP_WINS -> getPvpWinsAchievement(user);
        };
    }

    private String getPvpWinsAchievement(User user) {
        return MessageBundle.getMessage("achievement_pvp_wins") +  " "  + achievementService.getUsersAchievements(user).getPvpWins();
    }

    private String getCardLevelAchievement(User user) {
        return MessageBundle.getMessage("achievement_card_level") +  " "  + achievementService.getUsersAchievements(user).getCardLeveledUp();
    }

    private String getTasksAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_tasks") +  " "  + achievementService.getUsersAchievements(user).getTasksDone();
    }

    private String getMoneySpentAchievementMessage(User user) {
        return MessageBundle.getMessage("achievement_money_spent") +  " "  + achievementService.getUsersAchievements(user).getMoneySpent();
    }

    @Override
    public String getQuestShopMessage(QuestType questType, long step) {
        return MessageBundle.getMessage("quest_shop");
    }

    @Override
    public String getQuestMessage(QuestType quest, long step) {
        return MessageBundle.getMessage(quest.name().toLowerCase() + "." + step);
    }

    @Override
    public String getQuestFinishMessage(QuestState state) {
        long lost = state.getDeaths();
        if(MessageBundle.getMessageMode().equals(MessageMode.SCH9))
            return "ICT: "  + String.format("%.0f", Math.max(100 - Math.random() * 10 * lost, 40)) + ",  MAT:  "  +  String.format("%.0f", Math.max(100 - Math.random() * 10 * lost, 27))  + ",  RUS:  "  +  String.format("%.0f", Math.max(100 - Math.random() * 10 * lost, 24));
        else
            return "";
    }
}
