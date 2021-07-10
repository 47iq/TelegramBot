package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import game.dungeon.CaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which forces user to enter randomly generated cave
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /dungeon_next
 */

@Component
public class EnterNextCaveCommand implements Command {
    @Autowired
    CaveService caveService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(commandDTO.getUser().getTokens() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nomoney2"), KeyboardType.LEAF, null, null);
        return caveService.enterNextCave(commandDTO);
    }
}
