package command.service_command;

import command.Command;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.UserService;
import game.entity.LootBoxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which makes user open a special lootbox.
 * Service command, used in other commands and code only.
 * @see OpenBoxCommand
 */

@Component
public class OpenSuperRareBoxCommand implements Command {
    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;


    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return openBoxCommand.execute(commandDTO, LootBoxType.SUPER_RARE);
    }
}
