package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.User;

/**
 * Command, which displays a stats menu.
 * Syntax: /stats
 */

public class StatsMenuCommand implements Command {

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, null, KeyboardType.STATS, null, null, user);
    }
}
