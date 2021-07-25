package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which displays help for dungeon.
 * Syntax: /dungeon_info
 */

@Component
public class DungeonInfoCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getDungeonInfo(), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
    }
}
