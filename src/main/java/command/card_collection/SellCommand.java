package command.card_collection;

import command.Command;
import data.CardService;
import game.Card;
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

@Component
public class SellCommand implements Command {

    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.forEach(x -> cardReferences.put("/sell_card." + x.getUID(),
                "id: " + x.getUID() + " " + MessageBundle.getMessage(x.getName().name() + "_short") + " " + messageFormatter.getPriceMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences);
    }
}