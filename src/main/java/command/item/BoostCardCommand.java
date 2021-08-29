package command.item;

import command.Command;
import game.service.CardService;
import game.service.UserService;
import game.entity.Card;
import game.service.BattleService;
import game.service.ImageParser;
import communication.keyboard.KeyboardType;
import game.entity.User;
import game.entity.ImageIdentifier;
import game.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;
import util.MessageBundle;
import util.MessageFormatter;

/**
 * Command, which uses boost item on card(if possible) and displays the card
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /boost_card.1
 */

@Component
public class BoostCardCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    UserService userService;
    @Autowired
    ImageParser imageParser;
    @Autowired
    BattleService battleService;
    @Autowired
    OccupationService occupationService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        User user = commandDTO.getUser();
        Card card = cardService.getMyCardById(id, user.getUID());
        if (card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.MENU, null, null, user, true);
        else {
            if(occupationService.isOccupied(card))
                return new AnswerDTO(false, MessageBundle.getMessage("err_occupied"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            if (userService.getBoostCount(user) < 1)
                return new AnswerDTO(false, MessageBundle.getMessage("err_noboost"), KeyboardType.MENU, null, null, user, true);
            if (card.getLevel() >= Long.parseLong(MessageBundle.getSetting("MAX_BOOST_LEVEL")))
                return new AnswerDTO(false, MessageBundle.getMessage("err_maxboost"), KeyboardType.MENU, null, null, user, true);
            if(cardService.boost(card))  {
                userService.spendBoost(user);
                return new AnswerDTO(true, MessageBundle.getMessage("success.boost") + "\n"
                        + messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                        imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null, user, true);
            } else {
                return new AnswerDTO(false, MessageBundle.getMessage("err_maxlvl"), KeyboardType.LEAF, null, null, user, true);
            }
        }
    }
}
