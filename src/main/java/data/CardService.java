package data;

import game.Card;
import model.User;

import java.util.List;

public interface CardService {
    List<Card> getAllCardsOf(User user);
    Card getMyCardById(long id, String owner);
}
