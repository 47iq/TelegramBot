package command.dungeon;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.dungeon.CaveService;
import game.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageFormatter;

@Component
public class MyTasksCommand implements Command {
    @Autowired
    TaskService taskService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, messageFormatter.getAllTasksMessage(taskService.getAll(commandDTO.getUser())), KeyboardType.DUNGEON_MENU, null, null, commandDTO.getUser(), true);
    }
}
