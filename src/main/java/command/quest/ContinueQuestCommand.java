package command.quest;

import command.Command;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.quest.QuestService;
import game.quest.QuestType;
import game.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContinueQuestCommand implements Command {
        @Autowired
        QuestService questService;

        @Override
        public AnswerDTO execute(CommandDTO commandDTO) {
            return questService.continueQuest(commandDTO.getUser());
        }
}
