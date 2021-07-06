package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import game.dungeon.CaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
