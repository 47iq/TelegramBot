package command.card_collection;

import command.Command;
import communication.util.MessageFormatter;
import data.CardService;
import game.entity.Card;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command, which displays all of the cards of user's collection.
 * @see NavigateToCardCommand
 * Syntax: /view
 */

@Component
public class CardViewCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.forEach(x -> cardReferences.put("/view_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser());
    }
}
