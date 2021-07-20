package command.tutorial;

import command.Command;
import command.service_command.RegisterCommand;
import data.UserDAO;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;

/**
 * Command, which displays start info and navigates user to start shop.
 * @see StartShopCommand
 * Syntax: /start
 */

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
        return new AnswerDTO(true, MessageBundle.getMessage("info_start"), KeyboardType.WELCOME, null, null, commandDTO.getUser());
    }
}
