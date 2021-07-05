package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;

@Component
public class StatsCommand implements Command {

    @Autowired
    MessageFormatter  messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getUserStats(commandDTO.getUser()), KeyboardType.LEAF, null, null);
    }
}
