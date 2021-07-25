package game.service;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import data.Achievement;
import data.AchievementDAO;
import data.User;
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
    public void addCave(User user) {
        if(achievementsEnabled() && achievementChecker.addCaveAndCheck(user))
            notificationService.notify(user, getAnswer(messageFormatter.getCaveAchievementMessage(user), user), 1);
    }

    @Override
    public void addBattle(User user) {
        if(achievementsEnabled() && achievementChecker.addBattleAndCheck(user))
            notificationService.notify(user, getAnswer(messageFormatter.getBattleAchievementMessage(user), user), 1);
    }

    @Override
    public void addBoxCave(User user) {
        if(achievementsEnabled() && achievementChecker.addBoxCaveAndCheck(user))
            notificationService.notify(user, getAnswer(messageFormatter.getBoxCaveAchievementMessage(user), user), 1);
    }

    @Override
    public void addCardsNumber(User user) {
        if(achievementsEnabled() && achievementChecker.addTotalCardsAndCheck(user))
            notificationService.notify(user, getAnswer(messageFormatter.getCardsAchievementMessage(user), user), 1);
    }

    @Override
    public Achievement getUsersAchievements(User user) {
        return achievementDAO.getEntityById(user.getUID());
    }

    private boolean achievementsEnabled() {
        //todo
        return true;
        //return Boolean.getBoolean(MessageBundle.getSetting("ACHIEVEMENTS_ENABLED"));
    }

    private AnswerDTO getAnswer(String caveAchievementMessage, User user) {
        return new AnswerDTO(true, caveAchievementMessage, KeyboardType.NONE, null, null, user, true);
    }

    @Override
    public boolean create(User user) {
        return achievementDAO.create(new Achievement(user.getUID()));
    }
}
