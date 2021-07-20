package data;

import util.MessageBundle;
import game.entity.Card;
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

    @Override
    public void heal(Card card) {
        card.heal();
        cardDAO.update(card);
    }

    @Override
    public boolean boost(Card card) {
        if(card.getLevel() < Long.parseLong(MessageBundle.getSetting("MAX_LEVEL"))) {
            card.levelUp();
            cardDAO.update(card);
            return true;
        } else
            return false;
    }

    @Override
    public void delete(Card card) {
        cardDAO.delete(card);
    }

    @Override
    public void save(Card secondCard) {
        cardDAO.update(secondCard);
    }

    @Override
    public List<Card> getAllCards() {
        return cardDAO.getAll();
    }

    @Override
    public boolean addXpLeveledUp(Card card, long xp) {
        Long needXpToLevelUp = card.calcNextLevelXp();
        if(needXpToLevelUp  ==  null)
            return false;
        System.out.println(needXpToLevelUp  +  "  "  +  xp  +  "  "  +  card.getXp());
        if(card.getXp() + xp >= needXpToLevelUp && card.getLevel() < Long.parseLong(MessageBundle.getSetting("MAX_LEVEL"))) {
            card.levelUp();
            card.setXp(card.getXp() - needXpToLevelUp + xp);
            cardDAO.update(card);
            return true;
        } else {
            card.setXp(card.getXp() + xp);
            cardDAO.update(card);
            return false;
        }
    }
}
