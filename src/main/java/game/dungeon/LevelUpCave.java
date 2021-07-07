package game.dungeon;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;

import java.util.ResourceBundle;

public class LevelUpCave implements Cave{
    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService, MessageFormatter messageFormatter, CardService cardService, UserService userService) {
        if(card.getLevel() < Long.parseLong(ResourceBundle.getBundle("messages").getString("MAX_LEVEL"))) {
            card.levelUp();
            cardService.save(card);
            return new AnswerDTO(true, messageFormatter.getLevelUpCaveMessage((long) (Math.random()*4), card), KeyboardType.DUNGEON, null, null);
        }  else {
            return new AnswerDTO(true, messageFormatter.getLevelUpCaveMaxLevelMessage((long) (Math.random()*4), card), KeyboardType.DUNGEON, null, null);
        }
    }
}
