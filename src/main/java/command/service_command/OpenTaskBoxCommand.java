package command.service_command;

import command.Command;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.AchievementType;
import game.service.UserService;
import game.entity.LootBoxType;
import game.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenTaskBoxCommand implements Command {
    @Autowired
    OpenBoxCommand openBoxCommand;
    @Autowired
    UserService userService;
    @Autowired
    AchievementService achievementService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        achievementService.addProgress(commandDTO.getUser(), AchievementType.BOX_CAVES);
        return openBoxCommand.execute(commandDTO, LootBoxType.TASK);
    }
}
