package command;

import command.Command;
import data.CardService;
import data.CardServiceImpl;
import game.Card;
import keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.AnswerDTO;
import util.CommandDTO;
import util.MessageBundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardViewCommand implements Command {
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> cardList = cardService.getAllCardsOf(commandDTO.getUser());
        Map<String, String> cardReferences = new HashMap<>();
        cardList.forEach(x -> cardReferences.put("/view_card." + x.getUID(), MessageBundle.getMessage(x.getName().name()) + " id: " + x.getUID()));
        return new AnswerDTO(true, MessageBundle.getMessage("ask_whatcard"), KeyboardType.CUSTOM, null, cardReferences);
    }
}
