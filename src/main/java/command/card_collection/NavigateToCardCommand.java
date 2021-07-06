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
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null);
        return new AnswerDTO(true, messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
    }
}
