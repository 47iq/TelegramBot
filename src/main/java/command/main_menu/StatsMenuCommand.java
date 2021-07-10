package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which displays a stats menu.
 * Syntax: /stats
 */

public class StatsMenuCommand implements Command {

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.STATS, null, null);
    }
}
