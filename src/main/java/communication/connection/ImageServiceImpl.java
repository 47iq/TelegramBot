package communication.connection;

import communication.keyboard.KeyboardCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import communication.util.AnswerDTO;

public class ImageServiceImpl implements ImageService{

    @Autowired
    KeyboardCreator creator;

    @Override
    public SendPhoto getPhoto(AnswerDTO answerDTO) {
        SendPhoto photo = new SendPhoto();
        photo.setPhoto(new InputFile(answerDTO.getImage()));
        photo.setCaption(answerDTO.getMessage());
        InlineKeyboardMarkup markup = creator.getKeyboard(answerDTO.getKeyboard(), answerDTO.getButtons());
        photo.setReplyMarkup(markup);
        return photo;
    }
}
