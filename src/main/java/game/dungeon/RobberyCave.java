package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.*;
import util.MessageFormatter;
import game.entity.User;
import game.entity.Card;

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
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService, UserBalanceService userBalanceService) {
        User user = commandDTO.getUser();
        userBalanceService.lowerBalance(commandDTO.getUser(), tokens);
        return new AnswerDTO(true, messageFormatter.getRobberyCaveMessage((long) (Math.random() * 4), tokens), KeyboardType.DUNGEON, null, null, user, true);
    }
}
