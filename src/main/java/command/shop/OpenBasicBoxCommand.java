package command.shop;

import command.Command;
import command.service_command.OpenBoxCommand;
import data.User;
import data.UserService;
import game.entity.LootBoxType;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;
import util.MessageBundle;

/**
 * Command, which makes user open a basic lootbox.
 * @see OpenBoxCommand
 * Syntax: /open_basic
 */

@Component
public class OpenBasicBoxCommand implements Command {

    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;


    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        long price = Long.parseLong(MessageBundle.getSetting("BASIC_COST"));
        if(userService.getBalance(commandDTO.getUser()) < price)
            return new AnswerDTO(true, MessageBundle.getMessage("err_nomoney"), KeyboardType.SHOP, null, null, user);
        else {
            userService.lowerBalance(commandDTO.getUser(), price);
            return openBoxCommand.execute(commandDTO, LootBoxType.BASIC);
        }
    }
}
