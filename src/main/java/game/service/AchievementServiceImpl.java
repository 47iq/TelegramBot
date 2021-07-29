package game.service;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import game.entity.Achievement;
import data.AchievementDAO;
import game.entity.AchievementType;
import game.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageFormatter;

@Component
public class AchievementServiceImpl implements AchievementService{

    @Autowired
    AchievementChecker achievementChecker;
    @Autowired
    NotificationService notificationService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    AchievementDAO achievementDAO;

    @Override
    public void addProgress(User user, AchievementType achievementType, long value) {
        if(achievementsEnabled() && achievementChecker.addAndCheck(user, achievementType, value))
            notificationService.notify(user, getAnswer(messageFormatter.getAchievementMessage(user, achievementType), user));
    }

    @Override
    public void addProgress(User user, AchievementType achievementType) {
        if(achievementsEnabled() && achievementChecker.addAndCheck(user, achievementType, 1))
            notificationService.notify(user, getAnswer(messageFormatter.getAchievementMessage(user, achievementType), user));
    }

    @Override
    public long getProgress(User user, AchievementType achievementType) {
        Achievement achievement = getUsersAchievements(user);
        return switch (achievementType) {
            case CARDS -> achievement.getTotalCards();
            case MONEY_SPENT -> achievement.getMoneySpent();
            case BOX_CAVES -> achievement.getBoxCavesNumber();
            case CAVES -> achievement.getCavesNumber();
            case BATTLES -> achievement.getBattlesNumber();
            case CARD_LEVEL -> achievement.getCardLeveledUp();
            case PVP_WINS -> achievement.getPvpWins();
            case TASKS -> achievement.getTasksDone();
        };
    }

    @Override
    public Achievement getUsersAchievements(User user) {
        return achievementDAO.getEntityById(user.getUID());
    }

    private boolean achievementsEnabled() {
        //todo
        return true;
    }

    private AnswerDTO getAnswer(String caveAchievementMessage, User user) {
        return new AnswerDTO(true, caveAchievementMessage, KeyboardType.NONE, null, null, user, true);
    }

    @Override
    public boolean create(User user) {
        return achievementDAO.create(new Achievement(user.getUID()));
    }
}
