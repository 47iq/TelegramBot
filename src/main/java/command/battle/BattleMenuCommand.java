package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which displays menu for battle commands.
 * Syntax: /battle_menu
 */
public class BattleMenuCommand implements Command {

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.BATTLE, null, null);
    }
}
