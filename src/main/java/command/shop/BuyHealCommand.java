package command.shop;

import command.Command;
import command.service_command.OpenBoxCommand;
import data.User;
import data.UserService;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;

/**
 * Command, which makes user buy a heal item.
 * Syntax: /buy_heal
 */

@Component
public class BuyHealCommand implements Command {
    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        long price = Long.parseLong(MessageBundle.getSetting("HEAL_COST"));
        if(userService.getBalance(commandDTO.getUser()) < price)
            return new AnswerDTO(true, MessageBundle.getMessage("err_nomoney"), KeyboardType.SHOP, null, null, user, true);
        else {
            userService.lowerBalance(commandDTO.getUser(), price);
            userService.addHeal(commandDTO.getUser());
            return new AnswerDTO(true, MessageBundle.getMessage("info_success") + "\n" + messageFormatter.getShopInfo(commandDTO.getUser()),KeyboardType.SHOP, null, null, user, true);
        }
    }
}
