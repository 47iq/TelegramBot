package command.quest;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.Card;
import game.quest.QuestService;
import game.quest.QuestType;
import game.service.CardService;
import game.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartFirstQuestCommand implements Command {
    @Autowired
    CardService cardService;
    @Autowired
    OccupationService occupationService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.stream()
                .filter(x -> !occupationService.isOccupied(x))
                .forEach(x -> cardReferences.put("/start_first_card." + x.getUID(), messageFormatter.getCardViewMessage(x)));
        cardReferences.put("/help", MessageBundle.getMessage("back"));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences, commandDTO.getUser(), true);
    }
}