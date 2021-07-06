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

public class TrapCave implements Cave {

    private final long lostHealth;

    public TrapCave() {
        lostHealth = (long) (Math.random() * 4 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        card.setHealth(card.getHealth() - lostHealth);
        cardService.save(card);
        if(card.getHealth()  >  0)
            return new AnswerDTO(true, messageFormatter.getTrapCaveMessage((long) (Math.random()*4), lostHealth, card), KeyboardType.DUNGEON, null, null);
        else
            return new AnswerDTO(true, messageFormatter.getTrapCaveDeadMessage((long) (Math.random()*4),lostHealth, card), KeyboardType.DUNGEON_LEAF, null, null);
    }
}
