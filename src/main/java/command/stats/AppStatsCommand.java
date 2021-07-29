package command.stats;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import game.service.CardService;
import game.entity.User;
import game.service.UserService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Command, which displays app stats.
 * Syntax: /app_stats
 */

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
        User user = commandDTO.getUser();
        List<User> userList = userService.getAllUsers();
        List<Card> cardList = cardService.getAllCards();
        return new AnswerDTO(true, messageFormatter.getAppStats(userList, cardList), KeyboardType.LEAF, null, null, user, true);
    }
}
