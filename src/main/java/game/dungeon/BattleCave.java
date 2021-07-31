package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.*;
import util.MessageFormatter;
import game.entity.Card;

/**
 * A Battle  cave class: performs a battle with a randomly generated enemy
 */

public class BattleCave implements Cave {

    public BattleCave() {
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService, UserBalanceService userBalanceService) {
        Enemy enemy = new Enemy(enemyWeightedRandomizer.getRandom());
        return battleService.battleEnemy(commandDTO, enemy, card);
    }

    public AnswerDTO battleEnemy(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService,
                                 UserService userService, OpenSuperRareBoxCommand command,
                                 WeightedRandomizer<EnemyType> enemyWeightedRandomizer,
                                 TaskService taskService, UserBalanceService userBalanceService, Enemy enemy) {
        return battleService.battleEnemy(commandDTO, enemy, card);
    }
}
