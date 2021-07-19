package command.stats;

import command.Command;
import communication.keyboard.KeyboardType;
import data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;

/**
 * Command, which displays user stats.
 * Syntax: /my_stats
 */

@Component
public class MyStatsCommand implements Command {

    @Autowired
    MessageFormatter  messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, messageFormatter.getUserStats(commandDTO.getUser()), KeyboardType.LEAF, null, null, user);
    }
}
