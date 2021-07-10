package command.shop;

import command.Command;
import command.service_command.OpenBoxCommand;
import data.UserService;
import game.entity.LootBoxType;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

/**
 * Command, which makes user open a pro lootbox.
 * @see OpenBoxCommand
 * Syntax: /open_pro
 */

@Component
public class OpenProBoxCommand implements Command {
    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;


    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long price = Long.parseLong(MessageBundle.getSetting("PRO_COST"));
        if(userService.getBalance(commandDTO.getUser()) < price)
            return new AnswerDTO(true, MessageBundle.getMessage("err_nomoney"), KeyboardType.SHOP, null, null);
        else {
            userService.lowerBalance(commandDTO.getUser(), price);
            return openBoxCommand.execute(commandDTO, LootBoxType.PRO);
        }
    }
}
