package game.quest;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import data.CardDAO;
import data.QuestDAO;
import game.entity.Card;
import game.entity.User;
import game.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class QuestServiceImpl implements QuestService {

    @Autowired
    QuestDAO questDAO;
    @Autowired
    Quest firstQuest;
    @Autowired
    CardService cardService;
    @Autowired
    CardDAO cardDAO;

    Set<QuestState> questStates = new HashSet<>();

    private final long MAX_STEP = Long.parseLong(MessageBundle.getSetting("FIRST_QUEST_MAX_STEP"));

    @PostConstruct
    void init() {
        questStates = new HashSet<>(questDAO.getAll());
    }

    @Override
    public AnswerDTO enterQuest(QuestType questType, User user, Card card) {
        QuestState foundQuestState = questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
        if (foundQuestState != null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_inquest"),
                    KeyboardType.LEAF, null, null, user, true);
        QuestState newQuestState = new QuestState(user.getUID(), card.getUID(), questType);
        questDAO.create(newQuestState);
        questStates.add(newQuestState);
        switch (questType) {
            case FIRST_QUEST ->  {
                AnswerDTO answerDTO  =  firstQuest.continueQuest(newQuestState, card, user);
                questDAO.update(newQuestState);
                return  answerDTO;
            }
        }
        return null;
    }

    @Override
    public AnswerDTO continueQuest(User user) {
        QuestState questState =  questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
        if (questState == null)
            return new AnswerDTO(false, MessageBundle.getMessage("err_notinquest"),
                    KeyboardType.LEAF, null, null, user, true);
        if(questState.getStep() > MAX_STEP)
            return new AnswerDTO(true, MessageBundle.getMessage("first_run_again"),
                    KeyboardType.QUEST_MENU, null, null, user, true);
        QuestType questType = questState.getType();
        Card card = cardService.getById(questState.getCardUID());
        switch (questType) {
            //todo
            default -> {
                AnswerDTO answerDTO = firstQuest.continueQuest(questState, card, user);
                if(answerDTO.getKeyboard().equals(KeyboardType.QUEST_FINISH)) {
                    questStates.remove(questState);
                    questDAO.delete(questState);
                } else
                    questDAO.update(questState);
                return answerDTO;
            }
        }
    }

    @Override
    public AnswerDTO pauseQuest(User user) {
        return null;
    }

    @Override
    public boolean isInQuest(User user) {
        return questStates.stream().anyMatch(x -> x.getUserUID().equals(user.getUID()));
    }

    @Override
    public boolean isInQuest(Card card) {
        return questStates.stream().anyMatch(x -> x.getCardUID() == (card.getUID()));
    }

    @Override
    public void setCard(User user, Card card) {
        questStates.stream()
                .filter(x -> x.getUserUID().equals(user.getUID()))
                .findAny()
                .ifPresent(questState -> questState.setCardUID(card.getUID()));
    }

    @Override
    public Card getCard(User user) {
        QuestState questState = questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
        if(questState != null) {
            return cardDAO.getEntityById(questState.getCardUID());
        } else
            return null;
    }

    @Override
    public QuestState getUserQuestState(User user) {
        return questStates.stream().filter(x -> x.getUserUID().equals(user.getUID())).findAny().orElse(null);
    }
}
