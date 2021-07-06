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

public class LootCave implements Cave {

    private final long tokens;

    public LootCave() {
        tokens = (long) (Math.random() * 300 + 100);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        userService.higherBalance(commandDTO.getUser(), tokens);
        return new AnswerDTO(true, messageFormatter.getLootCaveMessage((long) (Math.random()*4), tokens), KeyboardType.DUNGEON, null, null);
    }
}
