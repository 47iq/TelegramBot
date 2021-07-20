package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import game.entity.WeightedRandomizer;
import game.service.BattleService;

/**
 * Robbery cave class: removes random amount of tokens from user's balance
 */

public class RobberyCave implements Cave{

    private final long tokens;

    public RobberyCave() {
        tokens = (long) (Math.random() * 100 + 100);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        User user = commandDTO.getUser();
        userService.lowerBalance(commandDTO.getUser(), tokens);
        return new AnswerDTO(true, messageFormatter.getRobberyCaveMessage((long) (Math.random() * 4), tokens), KeyboardType.DUNGEON, null, null, user);
    }
}
