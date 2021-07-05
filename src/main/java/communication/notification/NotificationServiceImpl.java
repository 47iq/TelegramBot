package communication.notification;

import communication.keyboard.KeyboardCreator;
import communication.util.AnswerDTO;
import data.PSQLUserDAO;
import data.User;
import data.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Arrays;
import java.util.List;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    UserService userService;
    @Autowired
    KeyboardCreator keyboardCreator;

    private static final Logger LOGGER = LogManager.getLogger(PSQLUserDAO.class);

    @Override
    public void notify(User user, AnswerDTO answerDTO) {
        try {
            if (answerDTO.getImage() != null) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setPhoto(new InputFile(answerDTO.getImage()));
                sendPhoto.setChatId(String.valueOf(user.getChatID()));
                if (answerDTO.getMessage() != null)
                    sendPhoto.setCaption(answerDTO.getMessage());
                if (answerDTO.getKeyboard() != null)
                    sendPhoto.setReplyMarkup(keyboardCreator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons()));
                answerDTO.getBot().execute(sendPhoto);
            } else{
                SendMessage sendMessage  = new SendMessage();
                sendMessage.setChatId(String.valueOf(user.getChatID()));
                if (answerDTO.getMessage() != null)
                    sendMessage.setText(answerDTO.getMessage());
                if (answerDTO.getKeyboard() != null)
                    sendMessage.setReplyMarkup(keyboardCreator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons()));
                answerDTO.getBot().execute(sendMessage );
            }
        } catch (Exception e) {
            LOGGER.error("Error while sending response: " + Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAll(AnswerDTO answerDTO) {
        List<User> userList = userService.getAllUsers();
        for(var user: userList)
            notify(user, answerDTO);
    }
}
