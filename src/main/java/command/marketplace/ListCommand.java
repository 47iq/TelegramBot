package command.marketplace;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardService;
import game.entity.Card;
import game.marketplace.MarketplaceService;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListCommand implements Command {

    @Autowired
    MarketplaceService marketplaceService;
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    BattleService battleService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream()
                .filter(x -> !battleService.isBattling(x, commandDTO.getUser()))
                .forEach(x -> cardReferences.put("/list_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}
