package data;

import game.entity.User;

import java.util.List;

/**
 * User Data Access Object class
 */

public interface UserDAO {

    /**
     * Method that returns the whole table
     *
     * @return cards table
     */

    List<User> getAll();

    /**
     * Method that returns user by its UID
     *
     * @param UID user UID
     * @return user
     */

    User getEntityById(String UID);

    /**
     * Method that updates user
     *
     * @param user user
     * @return updated user
     */

    User update(User user);

    /**
     * Method that deletes user
     *
     * @param UID user UID
     * @return true - if user has been deleted, false - if not
     */

    boolean delete(String UID);

    /**
     * Method that puts user into DB
     *
     * @param user user
     * @return true - if user has been saved, false - if not
     */

    boolean create(User user);
}
