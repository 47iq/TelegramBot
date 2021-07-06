package communication.connection;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import communication.util.AnswerDTO;

public interface AnswerService {
    SendMessage getMessage(AnswerDTO answerDTO);
}
