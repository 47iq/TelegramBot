package command.shop;

import command.Command;
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
 * Command, which makes user redeem daily free tokens(if possible).
 * Syntax: /free_tokens
 */

@Component
public class DailyFreeTokensCommand implements Command {
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        if(userService.tryGetDailyBonus(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("info_success") + "\n" + messageFormatter.getShopInfo(commandDTO.getUser()), KeyboardType.SHOP, null, null, user, true);
        else
            return new AnswerDTO(true, MessageBundle.getMessage("err_notyet")+ "\n" + messageFormatter.getShopInfo(commandDTO.getUser()), KeyboardType.SHOP, null, null, user, true);
    }
}
