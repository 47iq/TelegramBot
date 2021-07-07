package command.stats;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStatsCommand implements Command {
    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        List<User> userList = userService.getAllUsers();
        List<Card> cardList = cardService.getAllCards();
        return new AnswerDTO(true, messageFormatter.getAppStats(userList, cardList), KeyboardType.LEAF, null, null);
    }
}
