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
 * Trap cave class: lowers card's hp by a random value
 */

public class TrapCave implements Cave {

    private final long lostHealth;

    public TrapCave() {
        lostHealth = (long) (Math.random() * 4 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService, UserBalanceService userBalanceService) {
        User user = commandDTO.getUser();
        card.setHealth(Math.max(card.getHealth() - lostHealth, 0));
        cardService.save(card);
        if(card.getHealth()  >  0)
            return new AnswerDTO(true, messageFormatter.getTrapCaveMessage((long) (Math.random()*4), lostHealth, card), KeyboardType.DUNGEON, null, null, user, true);
        else
            return new AnswerDTO(true, messageFormatter.getTrapCaveDeadMessage((long) (Math.random()*4),lostHealth, card), KeyboardType.DUNGEON_LEAF, null, null, user, true);
    }
}
