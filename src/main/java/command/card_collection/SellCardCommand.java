package command.card_collection;

import command.Command;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.PriceCalculator;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;

@Component
public class SellCardCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;
    @Autowired
    PriceCalculator priceCalculator;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null);
        long price = priceCalculator.calculatePrice(card);
        cardService.delete(card);
        userService.higherBalance(commandDTO.getUser(), price);
        return new AnswerDTO(true, MessageBundle.getMessage("info_sold"), KeyboardType.LEAF,
                null, null);
    }
}
