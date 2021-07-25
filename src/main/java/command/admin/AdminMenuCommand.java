package command.admin;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import org.springframework.beans.factory.annotation.Autowired;
import util.MessageFormatter;

public class AdminMenuCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.ADMIN, null, null, commandDTO.getUser(), true);
    }
}
