package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import game.entity.Task;
import game.service.BattleService;
import game.service.TaskService;
import game.service.WeightedRandomizer;
import util.MessageFormatter;

public class TaskCave implements Cave{

    @Override
    public AnswerDTO enterThisCave(CommandDTO commandDTO, Card card, BattleService battleService,
                                   MessageFormatter messageFormatter, CardService cardService, UserService userService, OpenSuperRareBoxCommand command, WeightedRandomizer<EnemyType> enemyWeightedRandomizer, TaskService taskService) {
        User user = commandDTO.getUser();
        Task task = taskService.getTask(user);
        if(task != null)
            return new AnswerDTO(true, messageFormatter.getTaskStartMessage(task), KeyboardType.DUNGEON, null, null, user, true);
        else
            return null;
    }
}
