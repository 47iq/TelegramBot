package game.dungeon;

import command.shop.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;

public class ArmorCave implements Cave{

    private final long armorBoost;

    public ArmorCave() {
        armorBoost = (long) (Math.random() * 1 + 1);
    }

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command) {
        card.setDefence(card.getDefence() + armorBoost);
        cardService.save(card);
        return new AnswerDTO(true, messageFormatter.getArmorCaveMessage((long) (Math.random()*4), armorBoost, card), KeyboardType.DUNGEON, null, null);
    }
}
