package command.marketplace;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardService;
import data.User;
import data.UserService;
import game.marketplace.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class CancelCardCommand implements Command {

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
        if(!marketplaceService.isPresent(id))
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        marketplaceService.cancel(id);
        return new AnswerDTO(true, MessageBundle.getMessage("info_marketplace.canceled"), KeyboardType.LEAF,
                null, null, commandDTO.getUser(), true);
    }
}
