package game.service;

import game.entity.User;
import data.UserDAO;

import java.util.List;

/**
 * Interface that provides methods for users. Changes users in DB as well.
 *
 * @see UserDAO
 */

public interface UserService {

    /**
     * Method that returns user's balance
     *
     * @param user user
     * @return user's balance
     */

    long getBalance(User user);

    /**
     * Method that lowers user's balance by a number
     *
     * @param user  user
     * @param price amount of money taken from user
     */

    void spendTokens(User user, long price);

    /**
     * Method that adds a boost item to user
     *
     * @param user user
     */

    void addBoost(User user);

    /**
     * Method that adds a heal item to user
     *
     * @param user user
     */

    void addHeal(User user);

    /**
     * Method that spends user's heal item
     *
     * @param user user
     */

    void spendHeal(User user);

    /**
     * Method that spends user's boost item
     *
     * @param user user
     */

    void spendBoost(User user);

    /**
     * Method that highers user's balance by a number
     *
     * @param user  user
     * @param price amount of money gained by user
     */

    void addTokens(User user, long price);

    /**
     * Method that highers user's balance by a number by user's  UID
     *
     * @param user  user
     * @param price amount of money gained by user
     */

    void addTokens(String user, long price);

    /**
     * Method that returns  heal items number for a user
     *
     * @param user user
     * @return heal items number
     */

    long getHealCount(User user);

    /**
     * Method that returns boost items number for a user
     *
     * @param user user
     * @return boost items number
     */

    long getBoostCount(User user);

    /**
     * Method that redeems a daily bonus for a user
     *
     * @param user user
     * @return true - if bonus has  been redeemed, false - if not
     */

    boolean tryGetDailyBonus(User user);

    /**
     * Updates user relying on a DB
     *
     * @param user user
     * @return user from DB
     */

    User getUserData(User user);

    /**
     * Method that saves user into DB
     *
     * @param user user
     * @see UserDAO#update(User)
     */

    void save(User user);

    /**
     * Method that returns the whole table of users
     *
     * @return user table
     * @see UserDAO#getAll()
     */

    List<User> getAllUsers();

    boolean create(User user);

    void subscribe(EventType eventType, User user);

    void unsubscribe(EventType eventType, User user);

}
