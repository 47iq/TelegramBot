package game.service;

import game.entity.AchievementType;
import game.entity.User;
import game.entity.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBalanceServiceImpl implements UserBalanceService{
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    AchievementService achievementService;

    @Override
    public void higherBalance(User user, long price) {
        userService.addTokens(user, price);
        taskService.addProgress(user, TaskType.MONEY_EARNED, price);
    }

    @Override
    public void higherBalance(String user, long price) {
        userService.addTokens(user, price);
        taskService.addProgress(userService.getUserData(new User(user, 0)), TaskType.MONEY_EARNED, price);
    }

    @Override
    public long getBalance(User user) {
        return userService.getBalance(user);
    }

    @Override
    public void lowerBalance(User user, long price) {
        userService.spendTokens(user, price);
        achievementService.addProgress(user, AchievementType.MONEY_SPENT, price);
    }
}
