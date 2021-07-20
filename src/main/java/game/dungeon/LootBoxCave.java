package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageFormatter;
import data.CardService;
import data.UserService;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;
import org.springframework.stereotype.Component;

/**
 * Loot box cave class: opens a special loot box on entrance
 */

@Component
public class LootBoxCave implements Cave {

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer) {
        AnswerDTO answerDTO = command.execute(commandDTO);
        answerDTO.setKeyboardType(KeyboardType.DUNGEON);
        return answerDTO.appendToBeginning(messageFormatter.getLootBoxCaveMessage(answerDTO.getCardName()));
    }
}
