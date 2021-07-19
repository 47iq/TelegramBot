package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import data.CardService;
import game.dungeon.CaveService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which puts user and card info dungeon list and executes EnterNextCaveCommand
 * @see EnterNextCaveCommand
 * Note: Card UID must be sent as an argument in CommandDTO.
 * Syntax: /dungeon_enter_card.1
 */

@Component
public class EnterDungeonCardCommand implements Command {
    @Autowired
    CaveService caveService;
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.LEAF, null, null, commandDTO.getUser());
        if(card.getHealth() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nohealth"), KeyboardType.LEAF, null, null, commandDTO.getUser());
        if(commandDTO.getUser().getTokens() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nomoney2"), KeyboardType.LEAF, null, null, commandDTO.getUser());
        return caveService.enterCaves(commandDTO,  card);
    }
}
