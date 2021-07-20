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
 * Weapon cave class: permanently adds random value to card's attack
 */

public class WeaponCave implements Cave{

    private final long attackBoost;

    public WeaponCave() {
        attackBoost = (long) (Math.random() * 1 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        User user = commandDTO.getUser();
        card.setAttack(card.getAttack() + attackBoost);
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getWeaponCaveMessage((long) (Math.random()*4),attackBoost, card), KeyboardType.DUNGEON, null, null, user);
    }
}
