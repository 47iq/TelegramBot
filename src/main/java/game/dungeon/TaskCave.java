package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.*;
import game.entity.User;
import game.entity.Card;
import game.entity.Task;
import util.MessageFormatter;

public class TaskCave implements Cave{

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService, UserBalanceService userBalanceService) {
        User user = commandDTO.getUser();
        Task task = taskService.getTask(user);
        if(task != null)
            return new AnswerDTO(true, messageFormatter.getTaskStartMessage(task), KeyboardType.DUNGEON, null, null, user, true);
        else
            return null;
    }
}
