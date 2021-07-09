package communication.connection;

import command.CommandFactory;
import communication.util.MessageBundle;
import data.PSQLUserDAO;
import data.UserService;
import data.User;
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
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

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
    @Autowired
    private UserService userService;

    @Override
    public String getBotUsername() {
        return MessageBundle.getSetting("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return MessageBundle.getSetting("API_KEY");
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
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
            } else
                return;
            LOGGER.info("Message from " + username + " has been received. Text: \"" + messageText + "\". Arg: " + arg);
            User user = new User(username, chatId);
            User cachedUser = userService.getUserData(user);
            if(cachedUser != null)
                user = cachedUser;
            CommandDTO commandDTO = new CommandDTO(user, messageText, arg, this);
            AnswerDTO answerDTO = commandFactory.execute(commandDTO);
            LOGGER.info("Answer to " + username + " has been prepared."+
                    "\". Keyboard: " +  answerDTO.getKeyboard() +  ".");
            if(answerDTO.getImage() == null) {
                SendMessage sendMessage = answerService.getMessage(answerDTO);
                sendMessage.setChatId(String.valueOf(chatId));
                try {
                    this.execute(sendMessage);
                } catch (Exception e) {
                    LOGGER.error("Error while sending response to "  +  username + ": " + e.getClass());
                    e.printStackTrace();
                }
            }
            else {
                SendPhoto sendPhoto = imageService.getPhoto(answerDTO);
                sendPhoto.setChatId(String.valueOf(chatId));
                try {
                    this.execute(sendPhoto);
                } catch (Exception e) {
                    LOGGER.error("Error while sending response to "  +  username + ": " + e.getClass());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during handling request: " + e.getClass());
            e.printStackTrace();
        }
    }
}
