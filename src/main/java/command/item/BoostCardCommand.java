package command.item;

import command.Command;
import data.CardService;
import data.UserService;
import game.Card;
import game.ImageBase;
import communication.keyboard.KeyboardType;
import data.User;
import game.ImageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;

@Component
public class BoostCardCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    UserService userService;
    @Autowired
    ImageBase imageBase;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        User user = commandDTO.getUser();
        Card card = cardService.getMyCardById(id, user.getUID());
        if (card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null);
        else {
            if (userService.getBoostCount(user) < 1)
                return new AnswerDTO(false, MessageBundle.getMessage("err_noboost"), KeyboardType.CLASSIC, null, null);
            userService.spendBoost(user);
            cardService.boost(card);
            return new AnswerDTO(true, MessageBundle.getMessage("info_succboost") + "\n"
                    + messageFormatter.getCardMessage(card), KeyboardType.LEAF,
                    imageBase.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
        }
    }
}
