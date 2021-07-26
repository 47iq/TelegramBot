package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.OccupationService;
import util.MessageBundle;
import util.MessageFormatter;
import data.CardService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command, which displays list of cards available for battle.
 * @see StartSearchCommand
 * Syntax: /battle
 */

@Component
public class PrepareBattleCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    OccupationService occupationService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream().filter(x  -> x.getHealth() > 0 && !occupationService.isOccupied(x)).forEach(x -> cardReferences.put("/battle_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}
