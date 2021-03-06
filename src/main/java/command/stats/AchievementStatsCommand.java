package command.stats;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.User;
import game.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageFormatter;

@Component
public class AchievementStatsCommand implements Command {
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    AchievementService achievementService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        return new AnswerDTO(true, messageFormatter.getUserAchievementsMessage(user), KeyboardType.LEAF, null, null, user, true);
    }
}
