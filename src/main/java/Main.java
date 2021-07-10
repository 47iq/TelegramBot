import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * A class that contains the start point of a program
 * @author 47iq
 * @version 2.0
 */

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext  context  =  new AnnotationConfigApplicationContext(AppConfig.class);
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean(TelegramLongPollingBot.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
