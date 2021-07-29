package command.shop;

import command.Command;
import command.service_command.OpenBoxCommand;
import game.entity.User;
import game.service.UserBalanceService;
import game.service.UserService;
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
    @Autowired
    UserBalanceService userBalanceService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        long price = Long.parseLong(MessageBundle.getSetting("BASIC_COST"));
        if(userBalanceService.getBalance(commandDTO.getUser()) < price)
            return new AnswerDTO(true, MessageBundle.getMessage("err_nomoney"), KeyboardType.SHOP, null, null, user, true);
        else {
            userBalanceService.lowerBalance(commandDTO.getUser(), price);
            return openBoxCommand.execute(commandDTO, LootBoxType.BASIC);
        }
    }
}
