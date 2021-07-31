package game.quest;

import communication.util.AnswerDTO;
import game.entity.Card;
import game.entity.User;

public interface Quest {
    AnswerDTO continueQuest(QuestState questState, Card card, User user);
}
