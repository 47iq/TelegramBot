package game.dungeon;

import command.shop.OpenBoxCommand;
import command.shop.OpenSuperRareBoxCommand;
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

@Component
public class LootBoxCave implements Cave {

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command) {
        AnswerDTO answerDTO = command.execute(commandDTO);
        answerDTO.setKeyboardType(KeyboardType.DUNGEON);
        return answerDTO.appendToBeginning(messageFormatter.getLootBoxCaveMessage(answerDTO.getCardName()));
    }
}
