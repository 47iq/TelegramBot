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
 * Command, which displays list of cards suitable for a heal item.
 * @see HealCardCommand
 * Syntax: /use_heal
 */


@Component
public class UseHealCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream().filter(x -> x.getHealth() < x.getMaxHealth()).forEach(x -> cardReferences.put("/heal_card." + x.getUID(),
                messageFormatter.getCardViewMessage(x) + " " + messageFormatter.getHealthMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser());
    }
}
