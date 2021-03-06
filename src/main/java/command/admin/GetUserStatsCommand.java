package command.admin;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import game.entity.User;
import game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which displays user info by his username.
 * Available for admin only.
 * Syntax sample: /user_stats.true_47iq
 */
@Component
public class GetUserStatsCommand implements Command {
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getUserStats(userService.getUserData(new User(commandDTO.getArg(), 0))), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
    }
}
