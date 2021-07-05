package data;

import game.Card;

import java.util.List;

public interface CardService {
    List<Card> getAllCardsOf(User user);
    Card getMyCardById(long id, String owner);
    void heal(Card card);
    void boost(Card card);
    void delete(Card card);
    void save(Card secondCard);
}
