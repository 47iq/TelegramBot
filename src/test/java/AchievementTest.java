import data.User;
import data.UserService;
import data.UserServiceImpl;
import game.entity.Card;
import game.entity.CardName;
import game.service.AchievementService;
import game.service.AchievementServiceImpl;
import game.service.BattleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AchievementTest extends Assert {
    UserService userService;
    AchievementService achievementService;

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        achievementService = context.getBean(AchievementServiceImpl.class);
        userService = context.getBean(UserServiceImpl.class);
    }

    @Test
    public void execute() {
        System.err.println("Running achievements tests:");
        for(User user: userService.getAllUsers()) {
            System.out.print(user + " ");
            if(achievementService.getUsersAchievements(user) ==  null)
                achievementService.create(user);
            assertNotNull(achievementService.getUsersAchievements(user));
            System.out.println(" has been completed");
        }
    }
}
