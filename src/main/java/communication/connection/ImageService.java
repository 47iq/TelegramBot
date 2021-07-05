package communication.connection;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import communication.util.AnswerDTO;

public interface ImageService {
    SendPhoto getPhoto(AnswerDTO answerDTO);
}
