package command.item;

import command.Command;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;
import game.service.ImageParser;
import communication.keyboard.KeyboardType;
import data.User;
import game.entity.ImageIdentifier;
import game.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;
import util.MessageBundle;
import util.MessageFormatter;

/**
 * Command, which uses heal item on card(if possible) and displays the card
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /boost_card.1
 */

@Component
public class HealCardCommand implements Command {
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
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, user, true);
        else {
            if (userService.getHealCount(user) < 1)
                return new AnswerDTO(false, MessageBundle.getMessage("err_noheal"), KeyboardType.CLASSIC, null, null, user, true);
            if(occupationService.isOccupied(card))
                return new AnswerDTO(false, MessageBundle.getMessage("err_occupied"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
            userService.spendHeal(user);
            cardService.heal(card);
            return new AnswerDTO(true, MessageBundle.getMessage("info_succheal") + "\n"
                    +  messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                    imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null, user, true);
        }
    }
}
