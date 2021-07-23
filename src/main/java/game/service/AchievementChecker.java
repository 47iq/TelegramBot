package game.service;

import data.User;

public interface AchievementChecker {
    boolean addCaveAndCheck(User user);
    boolean addBattleAndCheck(User user);
    boolean addBoxCaveAndCheck(User user);
    boolean addTotalCardsAndCheck(User user);
}
