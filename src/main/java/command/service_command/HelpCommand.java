package command.service_command;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which displays main menu.
 * Syntax: /help
 */

public class HelpCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.MENU, null, null, commandDTO.getUser(), true);
    }
}
