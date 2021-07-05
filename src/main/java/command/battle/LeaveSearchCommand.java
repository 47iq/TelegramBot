package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import game.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeaveSearchCommand implements Command {
    @Autowired
    BattleService battleService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        battleService.leaveSearch(commandDTO.getUser());
        return new AnswerDTO(true, MessageBundle.getMessage("info_success"), KeyboardType.LEAF, null, null);
    }
}
