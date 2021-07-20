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
 * A Heal cave class: adds random hp to a card
 */

public class HealCave implements Cave{

    private final long gainedHealth;

    public HealCave() {
        gainedHealth = (long) (Math.random() * 9 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        User user = commandDTO.getUser();
        card.setHealth(Math.min(card.getHealth() + gainedHealth, card.getMaxHealth()));
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getHealCaveMessage((long) (Math.random()*4),gainedHealth, card), KeyboardType.DUNGEON, null, null, user);
    }
}
