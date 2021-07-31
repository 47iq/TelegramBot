package game.quest;

import communication.util.AnswerDTO;
import game.entity.Card;
import game.entity.User;

public interface QuestService {
    AnswerDTO enterQuest(QuestType questType, User user, Card card);

    AnswerDTO continueQuest(User user);

    AnswerDTO pauseQuest(User user);

    void setCard(User user, Card card);

    Card getCard(User user);

    boolean isInQuest(User user);

    QuestState getUserQuestState(User user);
}
