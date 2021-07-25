package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import data.CardService;
import data.UserService;
import game.service.BattleService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command, which puts user and card info battle queue.
 * Note: Card UID must be sent as an argument in CommandDTO.
 * @see LeaveSearchCommand
 * Syntax: /battle_card.1
 */

@Component
public class StartSearchCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;
    @Autowired
    BattleService battleService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        long id = Long.parseLong(commandDTO.getArg());
        Card card = cardService.getMyCardById(id, commandDTO.getUser().getUID());
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        if(card.getHealth()  <=  0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_dead"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        if(battleService.isBattling(commandDTO.getUser()))
            return new AnswerDTO(false, MessageBundle.getMessage("err_insearch"), KeyboardType.CLASSIC, null, null, commandDTO.getUser(), true);
        battleService.startSearch(commandDTO.getUser(), card);
        return new AnswerDTO(true, MessageBundle.getMessage("info_startsearch"), KeyboardType.SEARCH_LEAF, null, null, commandDTO.getUser(), true);
    }
}
