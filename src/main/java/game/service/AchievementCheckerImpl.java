package game.service;

import data.Achievement;
import data.AchievementDAO;
import data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class AchievementCheckerImpl implements AchievementChecker {

    private static final Logger LOGGER = LogManager.getLogger(AchievementCheckerImpl.class);

    @Autowired
    AchievementDAO achievementDAO;

    @Override
    public boolean addCaveAndCheck(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setCavesNumber(Math.min(achievement.getCavesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("CAVES_NUM_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check cave achievement: " + e.getClass());
            return false;
        }
    }

    @Override
    public boolean addBattleAndCheck(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setBattlesNumber(Math.min(achievement.getBattlesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("BATTLES_NUM_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check battles achievement: " + e.getClass());
            return false;
        }
    }

    @Override
    public boolean addBoxCaveAndCheck(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setBoxCavesNumber(Math.min(achievement.getBoxCavesNumber() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("BOXCAVES_NUM_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check box caves achievement: " + e.getClass());
            return false;
        }
    }

    @Override
    public boolean addTotalCardsAndCheck(User user) {
        try {
            Achievement achievement = getUserAchievements(user);
            achievement.setTotalCards(Math.min(achievement.getTotalCards() + 1, Long.MAX_VALUE));
            achievementDAO.update(achievement);
            return achievement.getCavesNumber() == Long.parseLong(MessageBundle.getSetting("CARDS_NUM_ACHIEVEMENT"));
        }  catch (Exception e) {
            LOGGER.error("Can't check cards achievement: " + e.getClass());
            return false;
        }
    }

    private Achievement getUserAchievements(User user) {
        return achievementDAO.getEntityById(user.getUID());
    };
}
