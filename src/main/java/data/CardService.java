package data;

import game.entity.Card;

import java.util.List;

public interface CardService {
    List<Card> getAllCardsOf(User user);
    Card getMyCardById(long id, String owner);
    void heal(Card card);
    boolean boost(Card card);
    void delete(Card card);
    void save(Card secondCard);
    List<Card> getAllCards();
    boolean addXpLeveledUp(Card card, long xp);
}
