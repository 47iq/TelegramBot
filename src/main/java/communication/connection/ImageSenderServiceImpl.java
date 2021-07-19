package communication.connection;

import communication.keyboard.KeyboardCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import communication.util.AnswerDTO;

@Component
public class ImageSenderServiceImpl implements ImageSenderService {

    @Autowired
    KeyboardCreator creator;

    @Override
    public SendPhoto getPhoto(AnswerDTO answerDTO) {
        SendPhoto photo = new SendPhoto();
        photo.setPhoto(new InputFile(answerDTO.getImage()));
        photo.setCaption(answerDTO.getMessage());
        InlineKeyboardMarkup markup = creator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons(), answerDTO.getUser());
        photo.setReplyMarkup(markup);
        return photo;
    }
}
