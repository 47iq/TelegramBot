package command.admin;

import command.Command;
import game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Command, which adds 10000 tokens to the user by his username.
 * Available for admin only.
 * Syntax sample: /add_tokens.true_47iq
 */
@Component
public class AddTokensCommand implements Command {

    @Autowired
    UserService userService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        userService.addTokens(commandDTO.getArg(), 10000);
        return null;
    }
}
