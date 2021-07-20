package command.card_collection;

import command.Command;
import data.CardService;
import game.entity.Card;
import game.service.ImageParser;
import communication.keyboard.KeyboardType;
import game.entity.ImageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;
import util.MessageBundle;
import util.MessageFormatter;

/**
 * Command, which displays card info
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /view_card.1
 */

@Component
public class NavigateToCardCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    ImageParser imageParser;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, commandDTO.getUser());
        return new AnswerDTO(true, messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null, commandDTO.getUser());
    }
}
