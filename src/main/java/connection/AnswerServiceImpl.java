package connection;

import keyboard.KeyboardCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import util.AnswerDTO;
import util.MessageBundle;

import java.util.List;

@Component
public class AnswerServiceImpl implements AnswerService{

    private static final Logger logger = LogManager.getLogger(AnswerServiceImpl.class);
    @Autowired
    KeyboardCreator creator;

    @Override
    public SendMessage getMessage(AnswerDTO answerDTO) {
        SendMessage sendMessage = new SendMessage();
        if(answerDTO.isSuccessful()) {
            if(answerDTO.getMessage() != null)
                sendMessage.setText(answerDTO.getMessage());
            else
                sendMessage.setText(MessageBundle.getMessage("info_main"));
            logger.info("Prepared response, text: " + sendMessage.getText());
        } else {
            sendMessage.setText(answerDTO.getMessage());
            logger.warn("Prepared error response, text:  " + sendMessage.getText());
        }
        InlineKeyboardMarkup markup = creator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
}
