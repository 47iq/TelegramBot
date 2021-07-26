package data;

import game.entity.Card;

import java.util.List;

/**
 * Interface that provides methods for cards. Changes cards in DB as well.
 *
 * @see CardDAO
 */

public interface CardService {

    /**
     * Method that returns all of the cards, which have user's UID in owner field
     *
     * @param user user
     * @return all of user's cards
     */

    List<Card> getAllCardsOf(User user);

    /**
     * Method that returns card of the user by its UID if it is present
     *
     * @param id    card UID
     * @param owner user
     * @return card - if it is present, null - if not
     */

    Card getMyCardById(long id, String owner);

    /**
     * Method that sets card's health to max health
     *
     * @param card card
     */

    void heal(Card card);

    /**
     * Method that increments card level if it is possible(max level has not been reached)
     *
     * @param card card
     * @return true - if card has been leveled up, false - if not
     */

    boolean boost(Card card);

    /**
     * Method that deletes card from table
     *
     * @param card card
     * @see CardDAO#delete(Card)
     */

    void delete(Card card);

    /**
     * Method that saves card into DB
     *
     * @param card card
     * @see CardDAO#update(Card)
     */

    void save(Card card);

    /**
     * Method that returns the whole table
     *
     * @return cards table
     * @see CardDAO#getAll()
     */

    List<Card> getAllCards();

    /**
     * Method that adds xp to card and levels it up if it has enough xp for it
     *
     * @param card card
     * @param xp   gained xp
     * @return true - if card has reached a new level, false - if not
     */

    boolean addXpLeveledUp(Card card, long xp);

    Card getById(long id);

    void changeOwner(Card card, User user);
}
