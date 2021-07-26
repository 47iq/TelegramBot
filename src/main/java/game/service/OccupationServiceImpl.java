package game.service;

import data.User;
import data.UserService;
import game.dungeon.CaveService;
import game.entity.Card;
import game.marketplace.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OccupationServiceImpl implements OccupationService{

    @Autowired
    BattleService battleService;
    @Autowired
    CaveService caveService;
    @Autowired
    MarketplaceService marketplaceService;
    @Autowired
    UserService userService;

    @Override
    public boolean isOccupied(Card card) {
        User user = userService.getUserData(new User(card.getOwner(), 0));
        return battleService.isBattling(card, user) ||
                marketplaceService.isPresent(card.getUID());
    }
}
