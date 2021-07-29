package game.service;

import game.entity.User;

public interface UserBalanceService {
    /**
     * Method that highers user's balance by a number
     *
     * @param user  user
     * @param price amount of money gained by user
     */

    void higherBalance(User user, long price);

    /**
     * Method that highers user's balance by a number by user's  UID
     *
     * @param user  user
     * @param price amount of money gained by user
     */

    void higherBalance(String user, long price);

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

    void lowerBalance(User user, long price);
}
