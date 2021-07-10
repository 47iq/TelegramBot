package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;

/**
 * Command, which displays a shop menu.
 * Syntax: /shop
 */

@Component
public class ShopMenuCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, messageFormatter.getShopInfo(user), KeyboardType.SHOP, null, null);
    }
}
