package communication.connection;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import communication.util.AnswerDTO;

/**
 * Interface that provides methods for sending text answers.
 */
public interface TextSenderService {

    /**
     * Method that prepares text answer.
     *
     * @param answerDTO request answer
     * @return message that can be sent to user in Telegram messenger.
     */

    SendMessage getMessage(AnswerDTO answerDTO);
}
