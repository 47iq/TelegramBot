package connection;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import util.AnswerDTO;

@Component
public interface AnswerService {
    SendMessage getMessage(AnswerDTO answerDTO);
}
