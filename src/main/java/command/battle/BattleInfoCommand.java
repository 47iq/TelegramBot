package command.battle;

import command.Command;
import command.service_command.RegisterCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BattleInfoCommand implements Command {
    @Autowired
    RegisterCommand registerCommand;
    @Autowired
    UserDAO userDAO;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("info_battle2"), KeyboardType.LEAF, null, null);
    }
}
