package command.battle;

import command.Command;
import command.service_command.RegisterCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which displays help for battle commands.
 * Note: Battle info must have key "info_battle2"
 * Syntax: /battle_info
 */

@Component
public class BattleInfoCommand implements Command {
    @Autowired
    RegisterCommand registerCommand;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("info_battle2"), KeyboardType.LEAF, null, null, commandDTO.getUser());
    }
}
