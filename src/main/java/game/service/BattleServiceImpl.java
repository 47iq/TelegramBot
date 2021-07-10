package game.service;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.dungeon.Enemy;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.*;

@Component
public class BattleServiceImpl implements BattleService {

    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserService userService;
    @Autowired
    BattleXpCalculator battleXpCalculator;

    TelegramLongPollingBot bot;

    final static Map<User, Card> battleQueue = new HashMap<>();

    public BattleServiceImpl() {
        monitorQueue();
    }

    /**
     * Thread that monitors queue changes and pairs users into battles
     */

    private void monitorQueue() {
        new Thread(() -> {
            while (true) {
                try {
                    synchronized (battleQueue) {
                        battleQueue.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (battleQueue.size() >= 2)
                    prepareBattle();
            }
        }).start();
    }

    /**
     * Thread factory that prepares and starts a battle
     */

    private synchronized void prepareBattle() {
        new Thread(() -> {
            Set<User> users = battleQueue.keySet();
            User firstUser = users.stream().max((x, y) -> (int) (Math.random() * 10 - 5)).orElse(null);
            Card firstCard = battleQueue.get(firstUser);
            users.remove(firstUser);
            User secondUser = users.stream().max((x, y) -> (int) (Math.random() * 10 - 5)).orElse(null);
            Card secondCard = battleQueue.get(secondUser);
            battleQueue.remove(firstUser);
            battleQueue.remove(secondUser);
            startBattle(firstUser, firstCard, secondUser, secondCard);
        }).start();
    }

    /**
     * Method that executes a battle between two users
     *
     * @param firstUser  first user
     * @param firstCard  first card
     * @param secondUser second user
     * @param secondCard second card
     */

    private void startBattle(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        StringBuilder battleHistory = new StringBuilder();
        battleHistory.append(messageFormatter.getBattleStartMessage(firstUser, firstCard, secondUser, secondCard));
        firstUser.addBattle();
        secondUser.addBattle();
        completeBattle(battleHistory, firstCard, secondCard);
        if (firstCard.getHealth() > 0) {
            battleHistory.append(messageFormatter.getWinLossMessage(firstUser, secondUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null);
            answerDTO.setBot(bot);
            cardService.save(secondCard);
            notificationService.notify(secondUser, answerDTO);
            notificationService.notify(firstUser, answerDTO.append(calcLevelUp(firstCard, secondCard)));
            firstUser.addWin();
            userService.higherBalance(firstUser, Long.parseLong(MessageBundle.getSetting("WIN_BONUS")));
            userService.higherBalance(secondUser, Long.parseLong(MessageBundle.getSetting("LOSS_BONUS")));
            userService.save(firstUser);
            userService.save(secondUser);
        } else {
            battleHistory.append(messageFormatter.getWinLossMessage(secondUser, firstUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null);
            answerDTO.setBot(bot);
            cardService.save(firstCard);
            notificationService.notify(firstUser, answerDTO);
            notificationService.notify(secondUser, answerDTO.append(calcLevelUp(secondCard, firstCard)));
            secondUser.addWin();
            userService.higherBalance(secondUser, Long.parseLong(MessageBundle.getSetting("WIN_BONUS")));
            userService.higherBalance(firstUser, Long.parseLong(MessageBundle.getSetting("LOSS_BONUS")));
            userService.save(firstUser);
            userService.save(secondUser);
        }
    }

    /**
     * Method that completes a battle between two cards
     *
     * @param battleHistory battle history message
     * @param firstCard     first card
     * @param secondCard    second card
     * @return battle history message
     */

    private StringBuilder completeBattle(StringBuilder battleHistory, Card firstCard, Card secondCard) {
        int turnCounter = 1;
        while (firstCard.getHealth() > 0 && secondCard.getHealth() > 0) {
            int random = (int) (Math.random() * 100);
            int multiplier = 1;
            double damageMultiplier = Math.random() * 0.5 + 0.75;
            if ((int) (Math.random() * 100) < 10)
                multiplier = 2;
            int chance = 50;
            if (turnCounter % 2 == 0) {
                chance += 40 / (Math.pow(secondCard.getDefence(), 0.5) + 1);
                if (chance > random) {
                    secondCard.setHealth(Math.max(secondCard.getHealth() - firstCard.getAttack() * multiplier * damageMultiplier, 0));
                    if (multiplier == 2)
                        battleHistory.append(MessageBundle.getMessage("battle_crit")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, firstCard.getAttack() * multiplier * damageMultiplier, secondCard.getHealth()));
                } else {
                    battleHistory.append(MessageBundle.getMessage("battle_miss")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, 0, secondCard.getHealth()));
                }
            } else {
                chance += 40 / (Math.pow(firstCard.getDefence(), 0.5) + 1);
                if (chance > random) {
                    firstCard.setHealth(Math.max(firstCard.getHealth() - secondCard.getAttack() * multiplier * damageMultiplier, 0));
                    if (multiplier == 2)
                        battleHistory.append(MessageBundle.getMessage("battle_crit")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(secondCard, firstCard, secondCard.getAttack() * multiplier * damageMultiplier, firstCard.getHealth()));
                } else {
                    battleHistory.append(MessageBundle.getMessage("battle_miss")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(secondCard, firstCard, 0, firstCard.getHealth()));
                }
            }
            turnCounter++;
        }
        return battleHistory;
    }

    @Override
    public AnswerDTO battleEnemy(CommandDTO commandDTO, Enemy enemy, Card card) {
        StringBuilder battleHistory = new StringBuilder();
        battleHistory.append(enemy.getEnemyType().getBattleMessage()).append("\n").append("\n");
        battleHistory.append(messageFormatter.getEnemyBattleStartMessage(enemy, card)).append("\n");
        if (enemy.getEnemyCard().getHealth() > 0) {
            int random = (int) (Math.random() * 2);
            if (card.getHealth() < enemy.getEnemyCard().getHealth() || card.getAttack() < enemy.getEnemyCard().getAttack())
                random = 0;
            if (random == 0)
                completeBattle(battleHistory, card, enemy.getEnemyCard());
            else
                completeBattle(battleHistory, enemy.getEnemyCard(), card);
            cardService.save(card);
        }
        User user = commandDTO.getUser();
        if (card.getHealth() > 0) {
            userService.higherBalance(user, enemy.getAward());
            return new AnswerDTO(true, battleHistory.toString() + "\n" +
                    messageFormatter.getEnemyBattleWinMessage(card, enemy),
                    KeyboardType.DUNGEON, null, null)
                    .append(calcLevelUp(card, enemy.getEnemyCard()));
        } else
            return new AnswerDTO(true, battleHistory.toString() + "\n" +
                    messageFormatter.getEnemyBattleLoseMessage(card, enemy), KeyboardType.DUNGEON_LEAF, null, null);
    }

    @Override
    public boolean isBattling(User user) {
        synchronized (battleQueue) {
            return battleQueue.containsKey(user);
        }
    }

    @Override
    public void startSearch(User user, Card card, TelegramLongPollingBot bot) {
        synchronized (battleQueue) {
            this.bot = bot;
            battleQueue.put(user, card);
            battleQueue.notify();
        }
    }

    @Override
    public void leaveSearch(User user) {
        synchronized (battleQueue) {
            battleQueue.remove(user);
        }
    }

    /**
     * Method that returns message containing level up or xp increasing message
     *
     * @param winner winner card
     * @param loser  loser card
     * @return message
     */

    private String calcLevelUp(Card winner, Card loser) {
        long gainedXp = battleXpCalculator.calcXp(winner, loser);
        String message = "";
        if (cardService.addXpLeveledUp(winner, gainedXp))
            message += messageFormatter.getLevelUpMessage(winner);
        else
            message += messageFormatter.getBattleXpMessage(winner, gainedXp);
        return message;
    }
}
