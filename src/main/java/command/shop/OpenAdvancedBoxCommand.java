package command.shop;

import command.Command;
import data.UserService;
import game.LootBoxType;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

import java.util.ResourceBundle;

@Component
public class OpenAdvancedBoxCommand implements Command {
    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;


    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long price = Long.parseLong(ResourceBundle.getBundle("settings").getString("ADVANCED_COST"));
        if(userService.getBalance(commandDTO.getUser()) < price)
            return new AnswerDTO(true, MessageBundle.getMessage("err_nomoney"), KeyboardType.SHOP, null, null);
        else {
            userService.lowerBalance(commandDTO.getUser(), price);
            return openBoxCommand.execute(commandDTO, LootBoxType.ADVANCED);
        }
    }
}