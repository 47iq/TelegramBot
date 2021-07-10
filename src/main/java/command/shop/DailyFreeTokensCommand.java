package command.shop;

import command.Command;
import data.UserService;
import communication.keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;

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
        if(userService.tryGetDailyBonus(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("info_success") + "\n" + messageFormatter.getShopInfo(commandDTO.getUser()), KeyboardType.SHOP, null, null);
        else
            return new AnswerDTO(true, MessageBundle.getMessage("err_notyet")+ "\n" + messageFormatter.getShopInfo(commandDTO.getUser()), KeyboardType.SHOP, null, null);
    }
}
