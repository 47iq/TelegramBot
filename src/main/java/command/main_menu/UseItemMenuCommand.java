package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which displays an item usage menu.
 * Syntax: /use_item
 */

public class UseItemMenuCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.ITEM, null, null);
    }
}
