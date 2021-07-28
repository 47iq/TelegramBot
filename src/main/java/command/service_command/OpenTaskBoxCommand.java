package command.service_command;

import command.Command;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.UserService;
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
        achievementService.addBoxCave(commandDTO.getUser());
        return openBoxCommand.execute(commandDTO, LootBoxType.TASK);
    }
}
