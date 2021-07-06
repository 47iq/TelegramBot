package game.dungeon;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class RobberyCave implements Cave{

    private final long tokens;

    public RobberyCave() {
        tokens = (long) (Math.random() * 100 + 100);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        userService.lowerBalance(commandDTO.getUser(), tokens);
        return new AnswerDTO(true, messageFormatter.getRobberyCaveMessage((long) (Math.random() * 4), tokens), KeyboardType.DUNGEON, null, null);
    }
}
