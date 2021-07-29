package game.service;

import game.entity.AchievementType;
import game.entity.User;

public interface AchievementChecker {
    boolean addAndCheck(User user, AchievementType achievementType, long value);
}
