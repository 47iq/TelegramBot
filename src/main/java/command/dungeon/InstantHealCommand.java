package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;
import game.service.CardService;
import game.entity.User;
import game.service.UserService;
import game.dungeon.CaveService;
import game.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which heals card without leaving dungeon
 * Syntax: /instant_heal
 */

@Component
public class InstantHealCommand implements Command {
    @Autowired
    CaveService caveService;
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    private static final Logger logger = LogManager.getLogger(InstantHealCommand.class);

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = userService.getUserData(commandDTO.getUser());
        Card card;
        try {
            card = cardService.getMyCardById(Long.parseLong(commandDTO.getArg()), user.getUID());
        } catch (NumberFormatException e) {
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.DUNGEON_LEAF, null, null, user, true);
        }
        if(commandDTO.getUser().getHealCount() <= 0) {
            return new AnswerDTO(false, MessageBundle.getMessage("err_noheal"), KeyboardType.DUNGEON_LEAF, null, null, user, true);
        } else if(card == null) {
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.DUNGEON_LEAF, null, null, user, true);
        } else {
            try {
                userService.spendHeal(user);
                cardService.heal(card);
                return new AnswerDTO(true, messageFormatter.getInstantHealMessage(card), KeyboardType.DUNGEON, null, null, user, true);
            } catch (Exception e)  {
                logger.error("Error during instant heal: " + e.getClass());
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.DUNGEON_LEAF, null, null, user, true);
            }
        }
    }
}
