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
import game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

@Component
public class QuestInstantHealCommand implements Command {
    @Autowired
    QuestService questService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    UserService userService;
    @Autowired
    CardService cardService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        Card card = questService.getCard(user);
        if (card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"),
                    KeyboardType.LEAF, null, null, user, true);
        commandDTO.setArg(String.valueOf(card.getUID()));
        if (userService.getHealCount(user) < 1)
            return new AnswerDTO(false, MessageBundle.getMessage("err_noheal"), KeyboardType.MENU, null, null, user, true);
        userService.spendHeal(user);
        cardService.heal(card);
        QuestState questState = questService.getUserQuestState(user);
        return new AnswerDTO(true, messageFormatter.getQuestShopMessage(questState.getType(), questState.getStep()),
                KeyboardType.QUEST_SHOP, null, null, user, true);
    }
}
