package game.dungeon;

import command.shop.OpenSuperRareBoxCommand;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;

public interface Cave {
    AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                            MessageFormatter messageFormatter, CardService cardService, UserService userService,  OpenSuperRareBoxCommand command);
}
