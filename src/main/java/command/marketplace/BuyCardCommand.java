package command.marketplace;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.CardService;
import game.entity.User;
import game.service.UserService;
import game.marketplace.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class BuyCardCommand implements Command {

    @Autowired
    MarketplaceService marketplaceService;
    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        User user = commandDTO.getUser();
        if(!marketplaceService.isPresent(id) || cardService.getById(id).getOwner().equals(user.getUID()))
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        if(marketplaceService.getCost(id) > userService.getBalance(user))
            return new AnswerDTO(false, MessageBundle.getMessage("err_nomoney"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
        marketplaceService.buy(id, user);
        return new AnswerDTO(true, MessageBundle.getMessage("info_bought"), KeyboardType.LEAF,
                null, null, commandDTO.getUser(), true);
    }
}
