package command.service_command;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import org.springframework.stereotype.Component;

@Component
public class BackCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(commandDTO.getKeyboardType() == null)
            return new AnswerDTO(true, null, KeyboardType.MENU, null, null, commandDTO.getUser(), true);
        return new AnswerDTO(true, null, commandDTO.getKeyboardType(), null, null, commandDTO.getUser(), true);
    }
}
