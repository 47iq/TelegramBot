package game.quest;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import data.QuestDAO;
import game.entity.Card;
import game.entity.User;
import game.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class QuestServiceImpl implements QuestService {

    @Autowired
    QuestDAO questDAO;
    @Autowired
    Quest firstQuest;
    @Autowired
    CardService cardService;

    Set<QuestState> questStates = new HashSet<>();

    @PostConstruct
    void init() {
        questStates = new HashSet<>(questDAO.getAll());
    }

    @Override
    public AnswerDTO enterQuest(QuestType questType, User user, Card card) {
        QuestState questState = questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
        if (questState != null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_inquest"),
                    KeyboardType.LEAF, null, null, user, true);
        return switch (questType) {
            case QUEST_1 -> firstQuest.continueQuest(new QuestState(user.getUID(), card.getUID(), questType), card, user);
        };
    }

    @Override
    public AnswerDTO continueQuest(User user) {
        QuestState questState =  questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
        if (questState == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_notinquest"),
                    KeyboardType.LEAF, null, null, user, true);
        QuestType questType = questState.getType();
        Card card = cardService.getById(questState.getCardUID());
        return switch (questType) {
            case QUEST_1 -> firstQuest.continueQuest(new QuestState(user.getUID(), card.getUID(), questType), card, user);
        };
    }

    @Override
    public AnswerDTO pauseQuest(User user) {
        return null;
    }

    @Override
    public boolean isInQuest(User user) {
        return questStates.stream().anyMatch(x -> x.getUserUID().equals(user.getUID()));
    }
}
