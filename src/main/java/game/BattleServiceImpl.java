package game;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
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

    TelegramLongPollingBot bot;

    final static Map<User, Card> battleQueue = new HashMap<>();

    public BattleServiceImpl() {
        monitorQueue();
    }

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

    private synchronized void prepareBattle() {
        new Thread(() -> {
            Set<User> users = battleQueue.keySet();
            User nullUser = users.stream().filter(x -> x.getTotalBattles() == 0).findAny().orElse(null);
            User firstUser;
            if(nullUser != null)
                firstUser = nullUser;
            else
                firstUser = users.stream().min(Comparator.comparing(x -> x.getTotalWins()/x.getTotalBattles())).orElse(null);
            Card firstCard = battleQueue.get(firstUser);
            users.remove(firstUser);
            User secNullUser = users.stream().filter(x -> x.getTotalBattles() == 0).findAny().orElse(null);
            User secondUser;
            if(secNullUser != null)
                secondUser = secNullUser;
            else
                secondUser = users.stream().min(Comparator.comparing(x -> x.getTotalWins()/x.getTotalBattles())).orElse(null);
            Card secondCard = battleQueue.get(secondUser);
            battleQueue.remove(firstUser);
            battleQueue.remove(secondUser);
            startBattle(firstUser, firstCard, secondUser, secondCard);
        }).start();
    }

    private void startBattle(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        int turnCounter = 0;
        StringBuilder battleHistory = new StringBuilder();
        battleHistory.append(messageFormatter.getBattleStartMessage(firstUser, firstCard, secondUser, secondCard));
        firstUser.addBattle();
        secondUser.addBattle();
        while (firstCard.getHealth() > 0 && secondCard.getHealth() > 0) {
            int random = (int) (Math.random() * 100);
            int multiplier = 1;
            if ((int) (Math.random() * 100) < 10) {
                multiplier = 2;
                battleHistory.append(MessageBundle.getMessage("battle_crit")).append("\n");
            }
            int chance = 10;
            if (turnCounter % 2 == 0) {
                chance += secondCard.getDefence() / firstCard.getAttack() / 2;
                if (chance < random) {
                    secondCard.setHealth(secondCard.getHealth() - firstCard.getAttack() * multiplier);
                    battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, firstCard.getAttack() * multiplier, secondCard.getHealth()));
                } else {
                    battleHistory.append(MessageBundle.getMessage("battle_miss")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, 0, secondCard.getHealth()));
                }
            } else {
                chance += firstCard.getDefence() / secondCard.getAttack() / 2;
                if (chance < random) {
                    firstCard.setHealth(firstCard.getHealth() - secondCard.getAttack() * multiplier);
                    battleHistory.append(messageFormatter.getBattleMessage(secondCard, firstCard, secondCard.getAttack() * multiplier, firstCard.getHealth()));
                } else {
                    battleHistory.append(MessageBundle.getMessage("battle_miss")).append("\n");
                    battleHistory.append(messageFormatter.getBattleMessage(secondCard, firstCard, 0, firstCard.getHealth()));
                }
            }
            turnCounter++;
        }

        if (firstCard.getHealth() > 0) {
            battleHistory.append(messageFormatter.getBattleWinMessage(firstCard));
            battleHistory.append(messageFormatter.getWinLossMessage(firstUser, secondUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null);
            answerDTO.setBot(bot);
            cardService.boost(firstCard);
            cardService.save(secondCard);
            notificationService.notify(firstUser, answerDTO);
            notificationService.notify(secondUser, answerDTO);
            firstUser.addWin();
            userService.higherBalance(firstUser, Long.parseLong(ResourceBundle.getBundle("settings").getString("WIN_BONUS")));
            userService.higherBalance(secondUser, Long.parseLong(ResourceBundle.getBundle("settings").getString("LOSS_BONUS")));
            userService.save(firstUser);
            userService.save(secondUser);
        } else {
            battleHistory.append(messageFormatter.getBattleWinMessage(secondCard));
            battleHistory.append(messageFormatter.getWinLossMessage(secondUser, firstUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null);
            answerDTO.setBot(bot);
            cardService.boost(secondCard);
            cardService.save(firstCard);
            notificationService.notify(firstUser, answerDTO);
            notificationService.notify(secondUser, answerDTO);
            secondUser.addWin();
            userService.higherBalance(secondUser, Long.parseLong(ResourceBundle.getBundle("settings").getString("WIN_BONUS")));
            userService.higherBalance(firstUser, Long.parseLong(ResourceBundle.getBundle("settings").getString("LOSS_BONUS")));
            userService.save(firstUser);
            userService.save(secondUser);
        }
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
}
