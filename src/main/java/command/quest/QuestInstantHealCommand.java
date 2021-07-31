package command.quest;

import command.Command;
import command.item.HealCardCommand;
import command.shop.BuyHealCommand;
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
public class QuestInstantHealCommand implements Command {
    @Autowired
    QuestService questService;
    @Autowired
    HealCardCommand useHealCommand;
    @Autowired
    MessageFormatter messageFormatter;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        Card card = questService.getCard(user);
        if(card == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nocard"),
                    KeyboardType.LEAF, null, null, user, true);
        commandDTO.setArg(String.valueOf(card.getUID()));
        useHealCommand.execute(commandDTO);
        QuestState questState = questService.getUserQuestState(user);
        return new AnswerDTO(true, messageFormatter.getQuestShopMessage(questState.getType(), questState.getStep()),
                KeyboardType.QUEST_SHOP, null, null, user, true);
    }
}
