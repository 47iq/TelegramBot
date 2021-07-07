package game.dungeon;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;

public class WeaponCave implements Cave{

    private final long attackBoost;

    public WeaponCave() {
        attackBoost = (long) (Math.random() * 1 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService, MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        card.setAttack(card.getAttack() + attackBoost);
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getWeaponCaveMessage((long) (Math.random()*4),attackBoost, card), KeyboardType.DUNGEON, null, null);
    }
}