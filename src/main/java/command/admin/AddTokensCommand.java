package command.admin;

import command.Command;
import data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

@Component
public class AddTokensCommand implements Command {

    @Autowired
    UserService userService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        userService.higherBalance(commandDTO.getArg(), 1000);
        return null;
    }
}
