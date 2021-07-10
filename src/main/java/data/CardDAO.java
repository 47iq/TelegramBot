package data;

import game.entity.Card;

import java.util.List;

/**
 * Card Data Access Object interface
 */

public interface CardDAO {

    /**
     * Method that returns the whole table
     *
     * @return cards table
     */

    List<Card> getAll();

    /**
     * Method that returns card by its UID
     *
     * @param UID card UID
     * @return card
     */

    Card getEntityById(long UID);

    /**
     * Method that updates card
     *
     * @param card card
     * @return updated card
     */

    Card update(Card card);

    /**
     * Method that deletes card
     *
     * @param card card
     * @return true - if card has been deleted, false - if not
     */

    boolean delete(Card card);

    /**
     * Method that puts card into DB
     *
     * @param card card
     * @return true - if card has been saved, false - if not
     */

    boolean create(Card card);
}
