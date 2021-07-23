package command.service_command;

import command.Command;
import communication.keyboard.KeyboardType;
import data.UserService;
import game.service.AchievementService;
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
    private UserService userService;
    @Autowired
    private AchievementService achievementService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(userService.create(commandDTO.getUser()) && achievementService.create(commandDTO.getUser()))
            return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null, commandDTO.getUser());
        else
            return new AnswerDTO(false, null, KeyboardType.CLASSIC, null, null, commandDTO.getUser());
    }
}
