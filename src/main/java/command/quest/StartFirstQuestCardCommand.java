package command.quest;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.quest.QuestService;
import game.quest.QuestType;
import game.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class StartFirstQuestCardCommand implements Command {
    @Autowired
    QuestService questService;
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return questService.enterQuest(QuestType.FIRST_QUEST, commandDTO.getUser(), cardService.getById(Long.parseLong(commandDTO.getArg())));
    }
}
