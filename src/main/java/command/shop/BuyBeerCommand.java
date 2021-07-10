package command.shop;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

/**
 * Command, which displays beer info for user.
 * Syntax: /buy_beer
 */

public class BuyBeerCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("info_beer"), KeyboardType.LEAF, null, null);
    }
}
