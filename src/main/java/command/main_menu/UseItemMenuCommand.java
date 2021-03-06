package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import game.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which displays an item usage menu.
 * Syntax: /use_item
 */

@Component
public class UseItemMenuCommand implements Command {

    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, messageFormatter.getItemMessage(commandDTO.getUser()), KeyboardType.ITEM, null, null, user, true);
    }
}
