package command.tutorial;

import command.Command;
import command.service_command.RegisterCommand;
import communication.keyboard.KeyboardType;
import data.UserService;
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
    UserService userService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(userService.getUserData(commandDTO.getUser()) == null)
            registerCommand.execute(commandDTO);
        return new AnswerDTO(true, MessageBundle.getMessage("info_start"), KeyboardType.WELCOME, null, null, commandDTO.getUser());
    }
}
