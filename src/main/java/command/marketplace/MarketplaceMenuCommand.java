package command.marketplace;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.marketplace.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketplaceMenuCommand implements Command {

    @Autowired
    MarketplaceService marketplaceService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.MARKETPLACE, null, null, commandDTO.getUser(), true);
    }
}
