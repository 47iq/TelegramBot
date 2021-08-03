package communication.connection;

import communication.keyboard.KeyboardCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import communication.util.AnswerDTO;
import util.MessageBundle;

@Component
public class TextSenderServiceImpl implements TextSenderService {

    @Autowired
    KeyboardCreator creator;

    @Override
    public SendMessage getMessage(AnswerDTO answerDTO) {
        if(!answerDTO.isMustSend())
            return null;
        SendMessage sendMessage = new SendMessage();
        if (answerDTO.isSuccessful()) {
            if (answerDTO.getMessage() != null)
                sendMessage.setText(answerDTO.getMessage());
            else
                sendMessage.setText(MessageBundle.getMessage("choose.option"));
        } else {
            sendMessage.setText(answerDTO.getMessage());
        }
        InlineKeyboardMarkup markup = creator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons(), answerDTO.getUser());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
}
