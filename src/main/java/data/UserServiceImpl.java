package data;

import game.service.EventType;
import util.MessageBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public long getBalance(User user) {
        return userDAO.getEntityById(user.getUID()).getTokens();
    }

    @Override
    public void lowerBalance(User user, long price) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setTokens(Math.max(oldUser.getTokens() - price,  0));
        userDAO.update(oldUser);
    }

    @Override
    public void addBoost(User user) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setBoostCount(oldUser.getBoostCount() + 1);
        userDAO.update(oldUser);
    }

    @Override
    public void addHeal(User user) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setHealCount(oldUser.getHealCount() + 1);
        userDAO.update(oldUser);
    }

    @Override
    public void spendHeal(User user) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setHealCount(oldUser.getHealCount() - 1);
        userDAO.update(oldUser);
    }

    @Override
    public void spendBoost(User user) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setBoostCount(oldUser.getBoostCount() - 1);
        userDAO.update(oldUser);
    }

    @Override
    public void higherBalance(User user, long price) {
        User oldUser = userDAO.getEntityById(user.getUID());
        oldUser.setTokens(oldUser.getTokens() + price);
        userDAO.update(oldUser);
    }

    @Override
    public long getHealCount(User user) {
        return userDAO.getEntityById(user.getUID()).getHealCount();
    }

    @Override
    public long getBoostCount(User user) {
        return userDAO.getEntityById(user.getUID()).getBoostCount();
    }

    @Override
    public boolean tryGetDailyBonus(User user) {
        User oldUser = userDAO.getEntityById(user.getUID());
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime old = oldUser.getLastTokensRedeemed();
        if(old.plusHours(24).compareTo(now) < 0) {
            higherBalance(user, Long.parseLong(MessageBundle.getSetting("DAILY_BONUS")));
            oldUser.setLastTokensRedeemed(LocalDateTime.now(ZoneId.systemDefault()));
            userDAO.update(user);
            return true;
        }
        else
            return false;
    }

    @Override
    public User getUserData(User user) {
        return userDAO.getEntityById(user.getUID());
    }

    @Override
    public void higherBalance(String user, long price) {
        User oldUser = userDAO.getEntityById(user);
        oldUser.setTokens(oldUser.getTokens() + price);
        userDAO.update(oldUser);
    }

    @Override
    public void save(User user) {
        userDAO.update(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    public boolean create(User user) {
        return userDAO.create(user);
    }

    @Override
    public void subscribe(EventType eventType, User user) {
        switch (eventType)  {
            case BATTLE_ENEMY -> {
                user.setIsSubscribedToBattle(true);
                userDAO.update(user);
            }
        }
    }

    @Override
    public void unsubscribe(EventType eventType, User user) {
        switch (eventType)  {
            case BATTLE_ENEMY -> {
                user.setIsSubscribedToBattle(false);
                userDAO.update(user);
            }
        }
    }
}
