package data;

import game.Card;

import java.util.List;

public interface CardDAO {
    List<Card> getAll();
    Card getEntityById(long UID);
    Card update(Card user);
    boolean delete(Card card);
    boolean create(Card user);
}
