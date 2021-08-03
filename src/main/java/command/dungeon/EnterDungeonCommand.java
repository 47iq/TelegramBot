package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.BattleService;
import game.service.OccupationService;
import util.MessageBundle;
import util.MessageFormatter;
import game.service.CardService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command, which displays all of the cards which are able to enter the dungeon.
 * @see EnterDungeonCardCommand
 * Syntax: /enter_dungeon
 */

@Component
public class EnterDungeonCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    BattleService battleService;
    @Autowired
    OccupationService occupationService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream().filter(x  -> x.getHealth() > 0)
                .filter(x -> !occupationService.isOccupied(x))
                .forEach(x -> cardReferences.put("/dungeon_enter_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));
        return new AnswerDTO(true, MessageBundle.getMessage("ask.what.card"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}
