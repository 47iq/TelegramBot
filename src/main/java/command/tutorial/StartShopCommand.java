package command.tutorial;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which displays start shop info and makes him open a basic lootbox.
 * @see command.shop.OpenBasicBoxCommand
 * Syntax: /start_shop
 */

@Component
public class StartShopCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, messageFormatter.getStartShopInfo(commandDTO.getUser()), KeyboardType.START_SHOP, null, null, user);
    }
}
