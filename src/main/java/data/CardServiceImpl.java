package data;

import game.Card;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardServiceImpl implements CardService{
    @Autowired
    CardDAO cardDAO;

    @Override
    public List<Card> getAllCardsOf(User user) {
        List<Card> allCards = cardDAO.getAll();
        return allCards.stream().filter(x -> x.getOwner().equals(user.getUID())).collect(Collectors.toList());
    }

    @Override
    public Card getMyCardById(long id, String owner) {
        return cardDAO
                .getAll()
                .stream()
                .filter(x -> (x.getOwner().equals(owner) && x.getUID().equals(id)))
                .findAny()
                .orElse(null);
    }
}
