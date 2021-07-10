package command.item;

import command.Command;
import data.CardService;
import game.entity.Card;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command, which displays list of cards suitable for a boost item.
 * @see BoostCardCommand
 * Syntax: /use_boost
 */

@Component
public class UseBoostCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream().filter(x -> x.getLevel() < Long.parseLong(MessageBundle.getSetting("MAX_BOOST_LEVEL"))).forEach(x -> cardReferences.put("/boost_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences);
    }
}
