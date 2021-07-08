package command.shop;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import data.UserService;
import game.entity.LootBoxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

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
