package command.admin;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which shows all of the current users' usernames.
 * Available for admin only.
 * Syntax sample: /all_users
 */

@Component
public class AllUsersCommand implements Command {
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getUsersStats(userService.getAllUsers()), KeyboardType.LEAF, null, null, commandDTO.getUser());
    }
}
