import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import util.AppMode;
import util.MessageBundle;
import util.MessageMode;

import java.util.Locale;
import java.util.Properties;

/**
 * A class that contains the start point of a program
 * @author 47iq
 * @version 2.0
 */

public class Main {
    public static void main(String[] args) {
        try {
            AppMode appMode;
            MessageMode messageMode;
            if(args.length >= 2) {
                try {
                    appMode = AppMode.getValue(args[0].toUpperCase());
                } catch (Exception e) {
                    System.err.println("Pass application mode as the first argument. Default value: RELEASE has been selected.");
                    appMode = AppMode.RELEASE;
                }
                try {
                    messageMode = MessageMode.getValue(args[1].toUpperCase());
                } catch (Exception e) {
                    System.err.println("Pass messages mode as the first argument. Default value: ITMO has been selected.");
                    messageMode = MessageMode.ITMO;
                }
            } else {
                try {
                    appMode = AppMode.getValue(System.getProperty("mode"));
                } catch (Exception e) {
                    System.err.println("Pass application mode as -Dmode option. Default value: RELEASE has been selected.");
                    appMode = AppMode.RELEASE;
                }
                try {
                    messageMode = MessageMode.getValue(System.getProperty("messages"));
                } catch (Exception e) {
                    System.err.println("Pass messages mode as -Dmessages. Default value: ITMO has been selected.");
                    messageMode = MessageMode.ITMO;
                }
            }
            MessageBundle.setMessageMode(messageMode);
            MessageBundle.setAppMode(appMode);
            ApplicationContext  context  =  new AnnotationConfigApplicationContext(AppConfig.class);
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean(TelegramLongPollingBot.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
