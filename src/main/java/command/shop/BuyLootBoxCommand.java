package command.shop;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;
import game.service.CardService;
import game.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuyLootBoxCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        if(cardService.getAllCardsOf(user).size() > Long.parseLong(MessageBundle.getSetting("MAX_CARDS")))
            return new AnswerDTO(true, MessageBundle.getMessage("err_maxcards"), KeyboardType.SHOP, null, null, user, true);
        else
            return new AnswerDTO(true, messageFormatter.getBuyLootboxInfo(user), KeyboardType.BUY_BOX, null, null, user, true);
    }
}
