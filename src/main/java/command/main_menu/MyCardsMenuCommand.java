package command.main_menu;

import command.Command;
import game.service.CardService;
import game.entity.User;
import game.entity.Card;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.List;

/**
 * Command, which displays a my collection menu.
 * Syntax: /my_cards
 */


@Component
public class MyCardsMenuCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        List<Card> userCards = cardService.getAllCardsOf(commandDTO.getUser());
        if(userCards.isEmpty())
            return new AnswerDTO(true, MessageBundle.getMessage("err_nocards"), KeyboardType.MENU, null, null, user, true);
        StringBuilder builder = new StringBuilder();
        builder.append(MessageBundle.getMessage("cards_yourcards")).append("\n");
        for(var card : userCards)
            builder.append(messageFormatter.getCardMessage(card)).append("\n");
        return new AnswerDTO(true, builder.toString(), KeyboardType.CARDS, null, null, user, true);
    }
}
