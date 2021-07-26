package command.marketplace;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardService;
import data.User;
import game.entity.Card;
import game.marketplace.MarketplaceService;
import game.marketplace.Merchandise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BuyCommand implements Command {

    @Autowired
    MarketplaceService marketplaceService;
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Merchandise> merchandises = marketplaceService.getAll();
        User user = commandDTO.getUser();
        Map<String, String> cardReferences = new HashMap<>();
        merchandises.stream()
                .filter(x -> !cardService.getById(x.getCardUID()).getOwner().equals(user.getUID()))
                .forEach(x -> cardReferences.put("/buy_card." + x.getCardUID(), "(id: " + x.getCardUID() + ") " + messageFormatter.getPriceMessage(x.getCost())));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        String message = messageFormatter.getMarketplaceCardsMessage(merchandises);
        if(message.isEmpty())
            message = MessageBundle.getMessage("info_marketplace.nocards");
        return new AnswerDTO(true,  message + "\n\n" + (cardReferences.size() > 1 ? MessageBundle.getMessage("ask_whatcard") : MessageBundle.getMessage("info_marketplace.none")), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}
