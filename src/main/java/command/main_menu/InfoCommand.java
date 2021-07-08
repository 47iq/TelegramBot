package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfoCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getMainMenuInfo(), KeyboardType.LEAF, null, null);
    }
}
