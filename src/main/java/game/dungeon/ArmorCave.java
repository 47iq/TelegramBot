package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.TaskService;
import util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;

/**
 * An Armor cave class: boosts card's defence
 */

public class ArmorCave implements Cave{

    private final long armorBoost;

    public ArmorCave() {
        armorBoost = (long) (Math.random() * 1 + 3);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService) {
        User user = commandDTO.getUser();
        card.setDefence(card.getDefence() + armorBoost);
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getArmorCaveMessage((long) (Math.random()*4), armorBoost, card), KeyboardType.DUNGEON, null, null, user, true);
    }
}
