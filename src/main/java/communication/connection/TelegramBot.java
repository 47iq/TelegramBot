package communication.connection;

import command.CommandFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import util.MessageBundle;
import game.service.UserService;
import game.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Class that manages requests from Telegram messenger.
 */

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(TelegramBot.class);

    @Autowired
    private CommandFactory commandFactory;
    @Autowired
    private TextSenderService textSenderService;
    @Autowired
    private ImageSenderService imageSenderService;
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

    /**
     * Update-handling method
     *
     * @param update update(message or replyMarkup click)
     */

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
                if (strings.length == 2) {
                    messageText = strings[0];
                    arg = strings[1];
                } else
                    messageText = message.getText();
                username = message.getFrom().getUserName();
                chatId = message.getChatId();
            } else if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
                String message = update.getCallbackQuery().getData();
                String[] strings = message.split("\\.");
                if (strings.length == 2) {
                    messageText = strings[0];
                    arg = strings[1];
                } else
                    messageText = message;
                username = update.getCallbackQuery().getFrom().getUserName();
                chatId = update.getCallbackQuery().getMessage().getChatId();
            } else
                return;
            LOGGER.info("Message from " + username + " has been received. Text: \"" + messageText + "\". Arg: " + arg);
            AnswerDTO answerDTO = handleRequest(messageText, username, arg, chatId);
            sendResponse(answerDTO, chatId, username);
        } catch (Exception e) {
            LOGGER.error("Error during handling request: " + e.getClass());
            e.printStackTrace();
        }
    }

    /**
     * Method that handles request.
     *
     * @param messageText command
     * @param username    telegram username of user
     * @param arg         command argument
     * @param chatId      chat id of user
     * @return answer on a request
     */

    private AnswerDTO handleRequest(String messageText, String username, String arg, long chatId) {
        User user = new User(username, chatId);
        User cachedUser = userService.getUserData(user);
        if (cachedUser != null)
            user = cachedUser;
        CommandDTO commandDTO = new CommandDTO(user, messageText, arg);
        AnswerDTO answerDTO = commandFactory.execute(commandDTO);
        LOGGER.info("Answer to " + username + " has been prepared." +
                "\". Keyboard: " + answerDTO.getKeyboard() + ".");
        return answerDTO;
    }

    /**
     * Method that sends a response.
     *
     * @param answerDTO answer on a request
     * @param chatId    chat id of user
     * @param username  telegram username of user
     */

    private void sendResponse(AnswerDTO answerDTO, long chatId, String username) {
        if (answerDTO.getImage() == null) {
            SendMessage sendMessage = textSenderService.getMessage(answerDTO);
            if (sendMessage == null)
                return;
            sendMessage.setChatId(String.valueOf(chatId));
            try {
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                 notifyAboutApiError(sendMessage, username);
            } catch (Exception e) {
                LOGGER.error("Error while sending response to " + username + ": " + e.getClass());
                e.printStackTrace();
            }
        } else {
            SendPhoto sendPhoto = imageSenderService.getPhoto(answerDTO);
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                this.execute(sendPhoto);
            } catch (TelegramApiException e) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                notifyAboutApiError(sendMessage, username);
            } catch (Exception e) {
                LOGGER.error("Error while sending response to " + username + ": " + e.getClass());
                e.printStackTrace();
            }
        }
    }

    private void notifyAboutApiError(SendMessage sendMessage, String username) {
        LOGGER.error("API error while sending response to " + username + ": ");
        try {
            sendMessage.setText(MessageBundle.getMessage("err_api"));
            this.execute(sendMessage);
        } catch (Exception ex) {
            LOGGER.error("Error while sending API error response to " + username + ": ");
            ex.printStackTrace();
        }
    }
}
