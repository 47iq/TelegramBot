package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which removes user from battle queue(if user is present there).
 * @see StartSearchCommand
 * Syntax: /leave_search
 */

@Component
public class LeaveSearchCommand implements Command {
    @Autowired
    BattleService battleService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        battleService.leaveSearch(commandDTO.getUser());
        return new AnswerDTO(true, MessageBundle.getMessage("info_success"), KeyboardType.LEAF, null, null, commandDTO.getUser());
    }
}
