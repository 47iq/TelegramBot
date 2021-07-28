package command.marketplace;

import command.Command;
import command.MultiStepCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.marketplace.MarketplaceService;
import game.marketplace.Merchandise;
import game.service.BattleService;
import game.service.OccupationService;
import game.service.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class ListCardCommand implements Command, MultiStepCommand {

    @Autowired
    MarketplaceService marketplaceService;
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

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        String[] strings = commandDTO.getArg().split("-");
        if(strings.length == 1) {
            long id = Long.parseLong(commandDTO.getArg());
            Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
            if (occupationService.isOccupied(card))
                return new AnswerDTO(false, MessageBundle.getMessage("err_occupied"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            if (card == null)
                return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            return new AnswerDTO(true, MessageBundle.getMessage("info_enter.price"), KeyboardType.NONE,
                    null, null, commandDTO.getUser(), true);
        }
        if(strings.length == 2) {
            long id = Long.parseLong(strings[0]);
            long price = Long.parseLong(strings[1]);
            Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
            if(price < 50 || price > 1000000)
                return new AnswerDTO(false, MessageBundle.getMessage("err_invalid.price"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            if (occupationService.isOccupied(card))
                return new AnswerDTO(false, MessageBundle.getMessage("err_occupied"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            if (card == null)
                return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            marketplaceService.list(new Merchandise(id, price));
                return new AnswerDTO(false, MessageBundle.getMessage("info_marketplace.listed") + " "
                        + price + MessageBundle.getMessage("info_price2") , KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
        } else {
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
        }
    }
}
