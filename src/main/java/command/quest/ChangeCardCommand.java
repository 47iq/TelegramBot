package command.quest;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.Card;
import game.entity.User;
import game.quest.QuestService;
import game.quest.QuestState;
import game.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

@Component
public class ChangeCardCommand implements Command {
    @Autowired
    QuestService questService;
    @Autowired
    CardService cardService;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        if(!questService.isInQuest(user))
            return new AnswerDTO(false, MessageBundle.getMessage("err_notinquest"),
                    KeyboardType.LEAF, null, null, user, true);
        QuestState questState = questService.getUserQuestState(user);
        questService.setCard(user, cardService.getById(Long.parseLong(commandDTO.getArg())));
        return new AnswerDTO(true, messageFormatter.getQuestShopMessage(questState.getType(), questState.getStep()),
                KeyboardType.QUEST_SHOP, null, null, user, true);
    }
}
