package data;

import java.util.List;

public interface UserService {
    long getBalance(User user);
    void lowerBalance(User user, long price);
    void addBoost(User user);
    void addHeal(User user);
    void spendHeal(User user);
    void spendBoost(User user);
    void higherBalance(User user, long price);
    void higherBalance(String user, long price);
    long getHealCount(User user);
    long getBoostCount(User user);
    boolean tryGetDailyBonus(User user);
    User getUserData(User user);
    void save(User user);
    List<User> getAllUsers();
}
