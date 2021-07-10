package command.service_command;

import command.Command;
import data.UserDAO;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which registers new user.
 * Service command, used in other commands and code only.
 */

@Component
public class RegisterCommand implements Command {
    @Autowired
    private UserDAO userDAO;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(userDAO.create(commandDTO.getUser()) )
            return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null);
        else
            return new AnswerDTO(false, null, KeyboardType.CLASSIC, null, null);
    }
}
