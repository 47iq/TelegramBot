package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.battle.DefenceType;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class BattleSetDefenceCommand implements Command {
    @Autowired
    BattleService battleService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if (battleService.setDefenceType(commandDTO.getUser(), DefenceType.valueOf(commandDTO.getArg().toUpperCase())))
            if (!battleService.isTurnReady(commandDTO.getUser()))
                return new AnswerDTO(true, MessageBundle.getMessage("info_waiting"), KeyboardType.NONE, null, null, commandDTO.getUser(), true);
            else
                return new AnswerDTO(false);
        else
            return new AnswerDTO(true, MessageBundle.getMessage("err_cant"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
    }
}
