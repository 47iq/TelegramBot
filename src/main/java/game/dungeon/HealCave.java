package game.dungeon;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class HealCave implements Cave{

    private final long gainedHealth;

    public HealCave() {
        gainedHealth = (long) (Math.random() * 9 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        card.setHealth(Math.min(card.getHealth() + gainedHealth, card.getMaxHealth()));
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getHealCaveMessage((long) (Math.random()*4),gainedHealth, card), KeyboardType.DUNGEON, null, null);
    }
}