package data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

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
        oldUser.setTokens(oldUser.getTokens() - price);
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
        if(now.plusHours(24).compareTo(old) < 0) {
            higherBalance(user, Long.parseLong(ResourceBundle.getBundle("settings").getString("DAILY_BONUS")));
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
}