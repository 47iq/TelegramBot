package command.stats;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.User;
import data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Command, which displays users top.
 * Syntax: /top_stats
 */

@Component
public class TopStatsCommand implements Command {
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        List<User> userList = userService.getAllUsers();
        return new AnswerDTO(true, messageFormatter.getGlobalStatsMessage(userList), KeyboardType.LEAF, null, null, user);
    }
}
