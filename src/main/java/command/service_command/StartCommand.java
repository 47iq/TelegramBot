package command.service_command;

import command.Command;
import data.UserDAO;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

@Component
public class StartCommand implements Command {
    @Autowired
    RegisterCommand registerCommand;
    @Autowired
    UserDAO userDAO;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(userDAO.getEntityById(commandDTO.getUser().getUID()) == null)
            registerCommand.execute(commandDTO);
        return new AnswerDTO(true, MessageBundle.getMessage("info_start"), KeyboardType.WELCOME, null, null);
    }
}
