package game.service;

import game.entity.Achievement;
import game.entity.AchievementType;
import game.entity.User;

public interface AchievementService {
    void addProgress(User user, AchievementType achievementType, long value);
    void addProgress(User user, AchievementType achievementType);
    long getProgress(User user, AchievementType achievementType);
    Achievement getUsersAchievements(User user);
    boolean create(User user);
}
