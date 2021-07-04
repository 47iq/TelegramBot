package connection;

import command.CommandFactory;
import data.PSQLUserDAO;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.AnswerDTO;
import util.CommandDTO;

import java.util.Arrays;
import java.util.ResourceBundle;

@Component
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    private static final Logger LOGGER = LogManager.getLogger(PSQLUserDAO.class);

    @Autowired
    private CommandFactory commandFactory;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ImageService imageService;

    @Override
    public String getBotUsername() {
        return ResourceBundle.getBundle("settings").getString("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return ResourceBundle.getBundle("settings").getString("API_KEY");
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("Update has  been received: " + update.getMessage());
        String messageText;
        String username;
        String arg = null;
        long chatId;
        if ((update.hasMessage() && update.getMessage().hasText())) {
            Message message = update.getMessage();
            String[] strings = message.getText().split("\\.");
            if(strings.length == 2) {
                messageText = strings[0];
                arg = strings[1];
            } else
                messageText = message.getText();
            username = message.getFrom().getUserName();
            chatId = message.getChatId();
        } else if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();
            String[] strings = message.split("\\.");
            if(strings.length == 2) {
                messageText = strings[0];
                arg = strings[1];
            } else
                messageText = message;
            username = update.getCallbackQuery().getFrom().getUserName();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        else
            return;
        CommandDTO commandDTO = new CommandDTO(new User(username, chatId), messageText, arg);
        AnswerDTO answerDTO = commandFactory.execute(commandDTO);
        if(answerDTO.getImage() == null) {
            SendMessage sendMessage = answerService.getMessage(answerDTO);
            sendMessage.setChatId(String.valueOf(chatId));
            try {
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                LOGGER.error("Error while sending response: " + Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }
        }
        else {
            SendPhoto sendPhoto = imageService.getPhoto(answerDTO);
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                this.execute(sendPhoto);
            } catch (TelegramApiException e) {
                LOGGER.error("Error while sending response: " + Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }
        }

    }
}
