package command.card_collection;

import command.Command;
import game.service.*;
import game.entity.Card;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;
import util.MessageBundle;

/**
 * Command, which sells card.
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /sell_card.1
 */

@Component
public class SellCardCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;
    @Autowired
    PriceCalculator priceCalculator;
    @Autowired
    BattleService battleService;
    @Autowired
    OccupationService occupationService;
    @Autowired
    UserBalanceService userBalanceService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(occupationService.isOccupied(card))
            return new AnswerDTO(false, MessageBundle.getMessage("err_occupied"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        long price = priceCalculator.calculatePrice(card);
        cardService.delete(card);
        userBalanceService.higherBalance(commandDTO.getUser(), price);
        return new AnswerDTO(true, MessageBundle.getMessage("info_sold"), KeyboardType.LEAF,
                null, null, commandDTO.getUser(), true);
    }
}
