import config.AppConfig;
import connection.Bot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.LogManager;

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
