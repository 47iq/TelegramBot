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
 * Level up cave class: levels up card if it hasn't reached level 10
 */

public class LevelUpCave implements Cave{
    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService, UserBalanceService userBalanceService) {
        User user = commandDTO.getUser();
        if(card.getLevel() < 10) {
            card.levelUp();
            cardService.save(card);
            return new AnswerDTO(true, messageFormatter.getLevelUpCaveMessage((long) (Math.random()*4), card), KeyboardType.DUNGEON, null, null, user, true);
        }  else {
            return new AnswerDTO(true, messageFormatter.getLevelUpCaveMaxLevelMessage((long) (Math.random()*4), card), KeyboardType.DUNGEON, null, null, user, true);
        }
    }
}
