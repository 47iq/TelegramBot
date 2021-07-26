package game.marketplace;

import data.User;
import game.entity.Card;

import java.util.List;

public interface MarketplaceService {
    void list(Merchandise merchandise);
    void cancel(long id);
    List<Merchandise> getAll();
    void buy(long uid, User user);
    boolean isPresent(long uid);
    boolean isPresent(User user);
    long getCost(long id);
}
