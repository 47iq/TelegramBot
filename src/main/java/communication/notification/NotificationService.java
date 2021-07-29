package communication.notification;

import communication.util.AnswerDTO;
import game.entity.User;

/**
 * Interface for sending async answers to users in Telegram messenger
 */

public interface NotificationService {

    /**
     * Method that sends message to a concrete user.
     *
     * @param user      the receiver of a message
     * @param answerDTO the message object
     */

    void notify(User user, AnswerDTO answerDTO);

    void notify(User user, AnswerDTO answerDTO, int delay);

    /**
     * Method that sends message to all users.
     *
     * @param answerDTO the message object
     */

    void notifyAll(AnswerDTO answerDTO);
}
