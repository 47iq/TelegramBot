package communication.connection;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import communication.util.AnswerDTO;

/**
 * Interface that provides methods for sending answers containing images.
 */

public interface ImageSenderService {

    /**
     * Method that prepares image-containing answer.
     *
     * @param answerDTO request answer
     * @return message that can be sent to user in Telegram messenger.
     */

    SendPhoto getPhoto(AnswerDTO answerDTO);
}
