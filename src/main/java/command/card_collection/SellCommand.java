package command.card_collection;

import command.Command;
import game.service.CardService;
import game.entity.Card;
import communication.keyboard.KeyboardType;
import game.service.BattleService;
import game.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command, which displays all of the user's cards with its prices.
 * @see SellCardCommand
 * Syntax: /sell
 */

@Component
public class SellCommand implements Command {

    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    BattleService battleService;
    @Autowired
    OccupationService occupationService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream()
                .filter(x -> !occupationService.isOccupied(x))
                .forEach(x -> cardReferences.put("/sell_card." + x.getUID(), messageFormatter.getCardViewMessage2(x) + " " + messageFormatter.getPriceMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("/back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask.what.card"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}
