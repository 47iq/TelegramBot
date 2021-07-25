package command.shop;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import data.User;

/**
 * Command, which displays beer info for user.
 * Syntax: /buy_beer
 */

public class BuyBeerCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, MessageBundle.getMessage("info_beer"), KeyboardType.LEAF, null, null, user, true);
    }
}
