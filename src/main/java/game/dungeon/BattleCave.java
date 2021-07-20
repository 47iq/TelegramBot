package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;

/**
 * A Battle  cave class: performs a battle with a randomly generated enemy
 */

public class BattleCave implements Cave {

    public BattleCave() {
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        Enemy enemy = new Enemy(enemyWeightedRandomizer.getRandom());
        return battleService.battleEnemy(commandDTO, enemy, card);
    }
}
