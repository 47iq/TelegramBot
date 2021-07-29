package game.service;

import game.entity.Achievement;
import data.AchievementDAO;
import game.entity.AchievementType;
import game.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class AchievementCheckerImpl implements AchievementChecker {

    private static final Logger LOGGER = LogManager.getLogger(AchievementCheckerImpl.class);

    @Autowired
    private AchievementDAO achievementDAO;

    public boolean addAndCheck(User user, AchievementType achievementType, long value) {
        return switch (achievementType) {
            case CARDS -> addCardsNumber(user);
            case CAVES -> addCave(user);
            case BOX_CAVES -> addBoxCave(user);
            case BATTLES -> addBattle(user);
            case MONEY_SPENT -> addMoneySpent(user, value);
            case TASKS -> addTasksDone(user);
            case CARD_LEVEL -> addCardLeveled(user);
            case PVP_WINS -> addPvpWins(user);
        };
    }

    private boolean addPvpWins(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setPvpWins(Math.min(achievement.getPvpWins() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getPvpWins() == Long.parseLong(MessageBundle.getSetting("PVP_WINS_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check card level achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addCardLeveled(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setCardLeveledUp(Math.min(achievement.getCardLeveledUp() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getTasksDone() == Long.parseLong(MessageBundle.getSetting("CARD_LEVEL_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check card level achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addTasksDone(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setTasksDone(Math.min(achievement.getTasksDone() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getTasksDone() == Long.parseLong(MessageBundle.getSetting("TASKS_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check tasks achievement: " + e.getClass());
            return false;
        }
    }

    private  boolean addCave(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setCavesNumber(Math.min(achievement.getCavesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("CAVES_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check cave achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addBattle(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setBattlesNumber(Math.min(achievement.getBattlesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("BATTLES_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check battles achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addBoxCave(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setBoxCavesNumber(Math.min(achievement.getBoxCavesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("BOX_CAVES_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check box caves achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addCardsNumber(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setTotalCards(Math.min(achievement.getTotalCards() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("CARDS_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check cards achievement: " + e.getClass());
            return false;
        }
    }

    private boolean addMoneySpent(User user, long value) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setMoneySpent(Math.min(achievement.getMoneySpent() + value, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getMoneySpent() >= Long.parseLong(MessageBundle.getSetting("MONEY_SPENT_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check money achievement: " + e.getClass());
            return false;
        }
    }

    private Achievement getUserAchievements(User user) {
        return achievementDAO.getEntityById(user.getUID());
    }
}
