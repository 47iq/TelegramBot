package command;

import data.CardService;
import game.Card;
import game.ImageBase;
import keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.*;

@Component
public class NavigateToCardCommand implements Command{
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    ImageBase imageBase;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.MENU, null, null);
        return new AnswerDTO(true, messageFormatter.getCardMessage(card), KeyboardType.MENU,
                imageBase.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
    }
}
