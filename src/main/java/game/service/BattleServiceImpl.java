package game.service;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.battle.AttackType;
import game.battle.BattleState;
import game.battle.BattleXpCalculator;
import game.battle.DefenceType;
import util.MessageBundle;
import util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.dungeon.Enemy;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    @Autowired
    AchievementService achievementService;
    @Autowired
    NotificationPublisher notificationPublisher;

    final static Map<User, Card> battleQueue = new HashMap<>();

    final static Map<User, Card> battlingCards = new HashMap<>();

    final static Map<User, LocalDateTime> queueTimes = new HashMap<>();

    final static Map<User, BattleState> battleRooms = new HashMap<>();

    final static Boolean monitor = true;

    static Map<Thread, LocalDateTime> threadTimes = new HashMap<>();

    static Map<Thread, BattleState> battleStateMap = new HashMap<>();

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
                    Set<User> toKick = new HashSet<>();
                    for (var user : queueTimes.keySet()) {
                        if (queueTimes.get(user).plusMinutes(5).isBefore(LocalDateTime.now())) {
                           toKick.add(user);
                        }
                    }
                    toKick.forEach(x -> {
                        AnswerDTO message = new AnswerDTO(true, messageFormatter.getBattleQueueTimeoutMessage(), KeyboardType.NONE, null, null, null, true);
                        notificationService.notify(x, message);
                        queueTimes.remove(x);
                        battleQueue.remove(x);
                    });
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                else
                    notificationPublisher.notify(EventType.BATTLE_ENEMY,
                            new AnswerDTO(true, messageFormatter.getBattleWaitingMessage(),
                                    KeyboardType.NONE, null, null, null, true));
            }
        }).start();
        new Thread(() -> {
            try {
                while (true) {
                    Set<Thread> toTerminate = new HashSet<>();
                    for (var battleThread : threadTimes.keySet()) {
                        if (threadTimes.get(battleThread).plusMinutes(2).isBefore(LocalDateTime.now())) {
                            toTerminate.add(battleThread);
                            continue;
                        }
                        if (threadTimes.get(battleThread).plusMinutes(1).isBefore(LocalDateTime.now())) {
                            BattleState battleState = battleStateMap.get(battleThread);
                            User leaver;
                            if (!battleState.getAttackerOptions().isReady()) {
                                if (!battleState.getDefenderOptions().isReady()) {
                                    AnswerDTO message = new AnswerDTO(true, messageFormatter.getBattleNotifyMessage(), KeyboardType.NONE, null, null, null, true);
                                    notificationService.notify(battleState.getAttacker(), message);
                                    notificationService.notify(battleState.getDefender(), message);
                                    continue;
                                } else
                                    leaver = battleState.getAttacker();
                            } else
                                leaver = battleState.getDefender();
                            AnswerDTO message = new AnswerDTO(true, messageFormatter.getBattleNotifyMessage(), KeyboardType.NONE, null, null, leaver, true);
                            notificationService.notify(leaver, message);
                        }
                    }
                    toTerminate.forEach(x -> {
                        threadTimes.remove(x);
                        battleStateMap.remove(x);
                        x.interrupt();
                    });
                    Thread.sleep(30000);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            //startBattle(firstUser, firstCard, secondUser, secondCard);
            startOnlineBattle(firstUser, firstCard, secondUser, secondCard);
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
        battlingCards.put(firstUser, firstCard);
        battlingCards.put(secondUser, secondCard);
        battleHistory.append(messageFormatter.getBattleStartMessage(firstUser, firstCard, secondUser, secondCard));
        completeFastBattle(battleHistory, firstCard, secondCard);
        firstUser.addBattle();
        secondUser.addBattle();
        achievementService.addBattle(firstUser);
        achievementService.addBattle(secondUser);
        if (firstCard.getHealth() > 0) {
            battleHistory.append(messageFormatter.getWinLossMessage(firstUser, secondUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null, firstUser, true);
            saveResults(firstUser, firstCard, secondUser, secondCard, answerDTO);
        } else {
            battleHistory.append(messageFormatter.getWinLossMessage(secondUser, firstUser));
            AnswerDTO answerDTO = new AnswerDTO(true, battleHistory.toString(), KeyboardType.LEAF, null, null, secondUser, true);
            saveResults(secondUser, secondCard, firstUser, firstCard, answerDTO);
        }
        battlingCards.remove(firstUser);
        battlingCards.remove(secondUser);
    }

    private void startOnlineBattle(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        battlingCards.put(firstUser, firstCard);
        battlingCards.put(secondUser, secondCard);
        BattleState battleState = new BattleState(firstUser, secondUser);
        Thread thread = new Thread(() -> {
            battleRooms.put(firstUser, battleState);
            battleRooms.put(secondUser, battleState);
            try {
                completeOnlineBattle(firstUser, firstCard, secondUser, secondCard, battleState);
            } catch (InterruptedException e) {
                manageTurnTimeout(firstUser, firstCard, secondUser, secondCard);
                return;
            }
            firstUser.addBattle();
            secondUser.addBattle();
            achievementService.addBattle(firstUser);
            achievementService.addBattle(secondUser);
            if (firstCard.getHealth() > 0) {
                notifyAndSaveResults(firstUser, firstCard, secondUser, secondCard);
            } else {
                notifyAndSaveResults(secondUser, secondCard, firstUser, firstCard);
            }
            battlingCards.remove(firstUser);
            battlingCards.remove(secondUser);

        });
        thread.start();
    }

    private void manageTurnTimeout(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        BattleState battleSnapshot = battleRooms.get(firstUser);
        User winner = battleSnapshot.getDefender();
        User loser = battleSnapshot.getAttacker();
        if (battleSnapshot.getAttackerOptions().isReady()) {
            winner = battleSnapshot.getAttacker();
            loser = battleSnapshot.getDefender();
        }
        Card winnerCard = firstCard;
        Card loserCard = secondCard;
        if (secondCard.getOwner().equals(winner.getUID())) {
            winnerCard = secondCard;
            loserCard = firstCard;
        }
        AnswerDTO opponentLeft = new AnswerDTO(true, messageFormatter.getOpponentLeaveMessage(), KeyboardType.NONE, null, null, firstUser, true);
        AnswerDTO youLeft = new AnswerDTO(true, messageFormatter.getLeaveMessage(), KeyboardType.NONE, null, null, firstUser, true);
        notificationService.notify(winner, opponentLeft);
        notificationService.notify(loser, youLeft);
        loserCard.setHealth(0);
        battleRooms.remove(firstUser);
        battleRooms.remove(secondUser);
        battlingCards.remove(firstUser);
        battlingCards.remove(secondUser);
        notifyAndSaveResults(winner, winnerCard, loser, loserCard);
    }

    private void notifyAndSaveResults(User firstUser, Card firstCard, User secondUser, Card secondCard) {
        String message = messageFormatter.getWinLossMessage(firstUser, secondUser);
        AnswerDTO answerDTO = new AnswerDTO(true, message, KeyboardType.LEAF, null, null, firstUser, true);
        saveResults(firstUser, firstCard, secondUser, secondCard, answerDTO);
    }

    private void saveResults(User firstUser, Card firstCard, User secondUser, Card secondCard, AnswerDTO answerDTO) {
        cardService.save(secondCard);
        notificationService.notify(secondUser, answerDTO);
        notificationService.notify(firstUser, answerDTO.append(calcLevelUp(firstCard, secondCard)));
        firstUser.addWin();
        userService.higherBalance(firstUser, Long.parseLong(MessageBundle.getSetting("WIN_BONUS")));
        userService.higherBalance(secondUser, Long.parseLong(MessageBundle.getSetting("LOSS_BONUS")));
        userService.save(firstUser);
        userService.save(secondUser);
    }

    private void completeOnlineBattle(User firstUser, Card firstCard, User secondUser, Card secondCard, BattleState battleState) throws InterruptedException {
        int turnCounter = 0;
        battleState.clear();
        battleState.setAttacker(firstUser);
        battleState.setDefender(secondUser);
        AnswerDTO attackFirstMessage = new AnswerDTO(true, messageFormatter.getBattleStartMessage(firstUser, firstCard, secondUser, secondCard) + "\n" + messageFormatter.getAttackChoiceMessage(), KeyboardType.BATTLE_ATTACK, null, null, firstUser, true);
        AnswerDTO defenceFirstMessage = new AnswerDTO(true, messageFormatter.getBattleStartMessage(firstUser, firstCard, secondUser, secondCard) + "\n" + messageFormatter.getDefenceChoiceMessage(), KeyboardType.BATTLE_DEFENCE, null, null, secondUser, true);
        notificationService.notify(firstUser, attackFirstMessage);
        notificationService.notify(secondUser, defenceFirstMessage);
        battleStateMap.put(Thread.currentThread(), battleState);
        while (firstCard.getHealth() > 0 && secondCard.getHealth() > 0) {
            threadTimes.put(Thread.currentThread(), LocalDateTime.now());
            synchronized (monitor) {
                while (!battleState.isTurnReady())
                    monitor.wait();
            }
            if (turnCounter % 2 == 0) {
                completeTurnAndNotify(firstUser, secondUser, firstCard, secondCard, battleState);
            } else {
                completeTurnAndNotify(secondUser, firstUser, secondCard, firstCard, battleState);
            }
            turnCounter++;
        }
        threadTimes.remove(Thread.currentThread());
    }

    private void completeTurnAndNotify(User firstUser, User secondUser, Card firstCard, Card secondCard, BattleState battleState) {
        String result = completeOnlineTurn(firstCard, secondCard, battleState);
        if (secondCard.getHealth() <= 0 || firstCard.getHealth() <= 0) {
            AnswerDTO message = new AnswerDTO(true, result, KeyboardType.NONE, null, null, firstUser, true);
            notificationService.notify(firstUser, message);
            notificationService.notify(secondUser, message);
            return;
        }
        battleState.clear();
        battleState.setAttacker(secondUser);
        battleState.setDefender(firstUser);
        AnswerDTO attackMessage = new AnswerDTO(true, result + "\n" + messageFormatter.getAttackChoiceMessage(), KeyboardType.BATTLE_ATTACK, null, null, secondUser, true);
        AnswerDTO defenceMessage = new AnswerDTO(true, result + "\n" + messageFormatter.getDefenceChoiceMessage(), KeyboardType.BATTLE_DEFENCE, null, null, firstUser, true);
        notificationService.notify(secondUser, attackMessage);
        notificationService.notify(firstUser, defenceMessage);
    }

    private String completeOnlineTurn(Card attackCard, Card defenceCard, BattleState battleState) {
        AttackType attackType = battleState.getAttackerOptions().getAttackType();
        DefenceType defenceType = battleState.getDefenderOptions().getDefenceType();
        switch (attackType) {
            case HEAL -> {
                double healValue = defenceCard.getAttack() / 4;
                attackCard.setHealth(Math.min(attackCard.getMaxHealth(), attackCard.getHealth() + healValue));
                return messageFormatter.getBattleHealMessage(attackCard, healValue);
            }
            case SELF_KILL -> {
                attackCard.setHealth(0);
                return messageFormatter.getBattleSelfKillMessage(attackCard);
            }
            default -> {
                if (attackType.getContre().equals(defenceType)) {
                    return messageFormatter.getBattleBlockMessage(attackCard, defenceCard, attackType);
                } else {
                    double hitChance = attackType.getBaseChance() + calcAdditionalHitChance(defenceCard);
                    double hitChanceRandomValue = Math.random();
                    if (hitChanceRandomValue > hitChance)
                        return messageFormatter.getBattleMissMessage(attackCard, attackType);
                    double hitPowerCoefficient = Math.random() * 0.5 + 0.75;
                    double hitPower = attackType.getMultiplier() * hitPowerCoefficient * attackCard.getAttack();
                    double criticalHitChance = Math.random();
                    String additionalMessage = "";
                    if (criticalHitChance > 0.9) {
                        hitPower *= 2;
                        additionalMessage += messageFormatter.getBattleCritMessage(attackType);
                    }
                    defenceCard.setHealth(Math.max(0, defenceCard.getHealth() - hitPower));
                    return additionalMessage + messageFormatter.getBattleHitMessage(attackCard, defenceCard, hitPower, attackType);
                }
            }
        }
    }

    @Override
    public boolean setAttackType(User user, AttackType attackType) {
        BattleState battleState = battleRooms.get(user);
        if (battleState == null)
            return false;
        if (battleState.getAttacker().equals(user)) {
            battleState.getAttackerOptions().setAttackType(attackType);
            synchronized (monitor) {
                monitor.notifyAll();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean setDefenceType(User user, DefenceType defenceType) {
        BattleState battleState = battleRooms.get(user);
        if (battleState == null)
            return false;
        if (battleState.getDefender().equals(user)) {
            battleState.getDefenderOptions().setDefenceType(defenceType);
            synchronized (monitor) {
                monitor.notifyAll();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isTurnReady(User user) {
        return battleRooms.get(user).isTurnReady();
    }

    private double calcAdditionalHitChance(Card defenceCard) {
        return 0.4 / (Math.pow(defenceCard.getDefence(), 0.5) + 1);
    }


    public void completeFastBattle(StringBuilder battleHistory, Card firstCard, Card secondCard) {
        int turnCounter = 0;
        while (firstCard.getHealth() > 0 && secondCard.getHealth() > 0) {
            int random = (int) (Math.random() * 100);
            int multiplier = 1;
            double damageMultiplier = Math.random() * 0.5 + 0.75;
            if ((int) (Math.random() * 100) < 10)
                multiplier = 2;
            int chance = 50;
            if (turnCounter % 2 == 0) {
                completeFastTurn(battleHistory, firstCard, secondCard, random, multiplier, damageMultiplier, chance);
            } else {
                completeFastTurn(battleHistory, secondCard, firstCard, random, multiplier, damageMultiplier, chance);
            }
            turnCounter++;
        }
    }

    private void completeFastTurn(StringBuilder battleHistory, Card firstCard, Card secondCard, int random, int multiplier, double damageMultiplier, int chance) {
        chance += calcAdditionalHitChance(secondCard) * 100;
        if (chance > random) {
            secondCard.setHealth(Math.max(secondCard.getHealth() - firstCard.getAttack() * multiplier * damageMultiplier, 0));
            if (multiplier == 2)
                battleHistory.append(MessageBundle.getMessage("battle_crit")).append("\n");
            battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, firstCard.getAttack() * multiplier * damageMultiplier, secondCard.getHealth()));
        } else {
            battleHistory.append(MessageBundle.getMessage("battle_miss")).append("\n");
            battleHistory.append(messageFormatter.getBattleMessage(firstCard, secondCard, 0, secondCard.getHealth()));
        }
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
                completeFastBattle(battleHistory, card, enemy.getEnemyCard());
            else
                completeFastBattle(battleHistory, enemy.getEnemyCard(), card);
            cardService.save(card);
        }
        User user = commandDTO.getUser();
        achievementService.addBattle(user);
        if (card.getHealth() > 0) {
            userService.higherBalance(user, enemy.getAward());
            return new AnswerDTO(true, battleHistory + "\n" +
                    messageFormatter.getEnemyBattleWinMessage(card, enemy),
                    KeyboardType.DUNGEON, null, null, user, true)
                    .append(calcLevelUp(card, enemy.getEnemyCard()));
        } else
            return new AnswerDTO(true, battleHistory + "\n" +
                    messageFormatter.getEnemyBattleLoseMessage(card, enemy), KeyboardType.DUNGEON_LEAF, null, null, user, true);
    }

    @Override
    public boolean isBattling(User user) {
        synchronized (battleQueue) {
            return battleQueue.containsKey(user) || battlingCards.containsKey(user);
        }
    }

    @Override
    public void startSearch(User user, Card card) {
        synchronized (battleQueue) {
            battleQueue.put(user, card);
            queueTimes.put(user, LocalDateTime.now());
            battleQueue.notify();
        }
    }

    @Override
    public void leaveSearch(User user) {
        synchronized (battleQueue) {
            battleQueue.remove(user);
        }
    }

    @Override
    public Card getBattlingCard(User user) {
        if(battleQueue.containsKey(user))
            return battleQueue.get(user);
        else
            return battlingCards.get(user);
    }

    @Override
    public boolean isBattling(Card card, User user) {
        return getBattlingCard(user) != null && getBattlingCard(user).equals(card);
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
