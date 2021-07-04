package command;

import data.CardService;
import game.Card;
import keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.AnswerDTO;
import util.CommandDTO;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyCardsCommand implements Command{
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<Card> userCards = cardService.getAllCardsOf(commandDTO.getUser());
        if(userCards.isEmpty())
            return new AnswerDTO(true, MessageBundle.getMessage("info_nocards"), KeyboardType.CLASSIC, null, null);
        StringBuilder builder = new StringBuilder();
        builder.append(MessageBundle.getMessage("info_yourcards")).append("\n");
        for(var card : userCards)
            builder.append(messageFormatter.getCardMessage(card)).append("\n");
        return new AnswerDTO(true, builder.toString(), KeyboardType.MENU, null, null);
    }
}
