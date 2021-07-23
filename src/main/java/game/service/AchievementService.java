package game.service;

import data.Achievement;
import data.User;

public interface AchievementService {
    void addCave(User user);
    void addBattle(User user);
    void addBoxCave(User user);
    void addCardsNumber(User user);
    Achievement getUsersAchievements(User user);
    boolean create(User user);
}
