package connection;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import util.AnswerDTO;

public interface ImageService {
    SendPhoto getPhoto(AnswerDTO answerDTO);
}
