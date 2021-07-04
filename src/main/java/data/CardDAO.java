package data;

import game.Card;
import model.User;

import java.util.List;

public interface CardDAO {
    List<Card> getAll();
    Card getEntityById(long UID);
    Card update(Card user);
    boolean delete(long UID);
    boolean create(Card user);
}
