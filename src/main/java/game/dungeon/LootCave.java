package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;

/**
 * Loot cave class: adds random amount of tokens to user's balance
 */

public class LootCave implements Cave {

    private final long tokens;

    public LootCave() {
        tokens = (long) (Math.random() * 300 + 100);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        User user = commandDTO.getUser();
        userService.higherBalance(commandDTO.getUser(), tokens);
        return new AnswerDTO(true, messageFormatter.getLootCaveMessage((long) (Math.random()*4), tokens), KeyboardType.DUNGEON, null, null, user, true);
    }
}
