package command.item;

import command.Command;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.ImageParser;
import communication.keyboard.KeyboardType;
import data.User;
import game.entity.ImageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;

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

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        User user = commandDTO.getUser();
        Card card = cardService.getMyCardById(id, user.getUID());
        if (card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, user);
        else {
            if (userService.getBoostCount(user) < 1)
                return new AnswerDTO(false, MessageBundle.getMessage("err_noboost"), KeyboardType.CLASSIC, null, null, user);
            if (card.getLevel() >= Long.parseLong(MessageBundle.getSetting("MAX_BOOST_LEVEL")))
                return new AnswerDTO(false, MessageBundle.getMessage("err_maxboost"), KeyboardType.CLASSIC, null, null, user);
            if(cardService.boost(card))  {
                userService.spendBoost(user);
                return new AnswerDTO(true, MessageBundle.getMessage("info_succboost") + "\n"
                        + messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                        imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null, user);
            } else {
                return new AnswerDTO(false, MessageBundle.getMessage("err_maxlvl"), KeyboardType.LEAF, null, null, user);
            }
        }
    }
}
