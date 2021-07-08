package game.dungeon;

import command.shop.OpenSuperRareBoxCommand;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class BattleCave implements Cave {

    Enemy enemy;

    public BattleCave() {
        enemy = new Enemy();
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command) {
        return battleService.battleEnemy(commandDTO, enemy, card);
    }
}
