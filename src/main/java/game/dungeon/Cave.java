package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;

/**
 * An interface of a cave in dungeon: performs some  action on entrance
 */

public interface Cave {

    /**
     * Method that performs an action depending on a cave type
     * @param commandDTO command DTO
     * @param card card
     * @param battleService battle service
     * @param messageFormatter message formatter
     * @param cardService card service
     * @param userService user service
     * @param command super rare box command
     * @param enemyWeightedRandomizer randomizer for enemies
     * @return answer on a request
     */

    AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                            MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer);
}
