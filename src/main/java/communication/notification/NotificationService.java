package communication.notification;

import communication.util.AnswerDTO;
import data.User;

public interface NotificationService {
    void notify(User user, AnswerDTO answerDTO);
    void notifyAll(AnswerDTO answerDTO);
}
