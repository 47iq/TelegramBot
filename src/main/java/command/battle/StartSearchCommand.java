package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import data.CardService;
import data.UserService;
import game.BattleService;
import game.Card;
import game.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"), KeyboardType.CLASSIC, null, null);
        if(card.getHealth()  <=  0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_dead"), KeyboardType.CLASSIC, null, null);
        if(battleService.isBattling(commandDTO.getUser()))
            return new AnswerDTO(false, MessageBundle.getMessage("err_insearch"), KeyboardType.CLASSIC, null, null);
        battleService.startSearch(commandDTO.getUser(), card, commandDTO.getBot());
        return new AnswerDTO(true, MessageBundle.getMessage("info_startsearch"), KeyboardType.LEAF, null, null);
    }
}
