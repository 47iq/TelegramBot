package command.dungeon;

import command.Command;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.dungeon.CaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which forces user to leave dungeon
 * Syntax: /leave_dungeon
 */

@Component
public class LeaveDungeonCommand implements Command {
    @Autowired
    CaveService caveService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return caveService.leaveCaves(commandDTO);
    }
}
