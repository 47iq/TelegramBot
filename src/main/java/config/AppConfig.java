package config;

import command.*;
import command.step.CommandStep;
import connection.*;
import data.*;
import game.*;
import keyboard.KeyboardCreator;
import keyboard.KeyboardCreatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import util.ImageIdentifier;
import util.MessageFormatter;
import util.MessageFormatterImpl;

import java.util.*;

@Configuration
public class AppConfig {

    Map<String, Command> commandMap = new HashMap<>();

    @Bean
    public TelegramLongPollingBot getBot()  {
        return new TelegramBot();
    }

    @Bean
    @Scope("singleton")
    public UserDAO getUserDAO() {
        return new PSQLUserDAO();
    }

    @Bean
    @Scope("singleton")
    public CardDAO getCardDAO() {
        return new PSQLCardDAO();
    }

    @Bean
    @Scope("singleton")
    public CommandFactory getCommandFactory(){
        commandMap.put("/register", getRegisterCommand());
        commandMap.put("/help", getHelpCommand());
        commandMap.put("/start", getHelpCommand());
        commandMap.put("/open_basic", getOpenBasicCommand());
        commandMap.put("/my_cards", getMyCardsCommand());
        commandMap.put("/view", getViewCommand());
        commandMap.put("/view_card", getNavigateToCardCommand());
        Map<Command, CommandStep> commandStepMap = new HashMap<>();
        commandStepMap.put(getRegisterCommand(), null);
        return new CommandFactoryImpl(commandMap, commandStepMap);
    }

    @Bean
    @Scope("singleton")
    public Command getMyCardsCommand() {
        return new MyCardsCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getViewCommand() {
        return new CardViewCommand();
    }

    @Bean
    @Scope("singleton")
    public Command getNavigateToCardCommand() {
        return new NavigateToCardCommand();
    }

    @Bean
    public Command getOpenBasicCommand() {
        return new OpenBasicBoxCommand();
    }

    @Bean
    @Scope("singleton")
    public HelpCommand getHelpCommand() {
        return new HelpCommand();
    }

    @Bean
    @Scope("singleton")
    public AnswerService getAnswerService() {
        return new AnswerServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public ImageService getImageService() {
        return new ImageServiceImpl();
    }

    @Bean
    @Scope("singleton")
    public ImageBase getImageBase(){
        Map<ImageIdentifier, String> pathMap = new HashMap<>();
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.BASIC), settings.getString("KLIMENKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.RARE), settings.getString("KLIMENKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.EPIC), settings.getString("KLIMENKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KLIMENKOV, CardType.LEGENDARY), settings.getString("KLIMENKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.BASIC), settings.getString("KOROBKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.RARE), settings.getString("KOROBKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.EPIC), settings.getString("KOROBKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.KOROBKOV, CardType.LEGENDARY), settings.getString("KOROBKOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.BASIC), settings.getString("GAVRILOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.RARE), settings.getString("GAVRILOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.EPIC), settings.getString("GAVRILOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.GAVRILOV, CardType.LEGENDARY), settings.getString("GAVRILOV_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.BASIC), settings.getString("BALAKSHIN_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.RARE), settings.getString("BALAKSHIN_RARE"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.EPIC), settings.getString("BALAKSHIN_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.BALAKSHIN, CardType.LEGENDARY), settings.getString("BALAKSHIN_LEGENDARY"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.BASIC), settings.getString("POLYAKOV_BASIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.RARE), settings.getString("POLYAKOV_RARE"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.EPIC), settings.getString("POLYAKOV_EPIC"));
        pathMap.put(new ImageIdentifier(CardName.POLYAKOV, CardType.LEGENDARY), settings.getString("POLYAKOV_LEGENDARY"));
        return new ImageBaseImpl(pathMap);
    }

    @Bean
    @Scope("singleton")
    public KeyboardCreator getKeyboardCreator() {
        List<String> buttons = new ArrayList<>(commandMap.keySet());
        System.out.println(buttons);
        return new KeyboardCreatorImpl(buttons);
    }

    @Bean
    @Scope("singleton")
    public MessageFormatter messageFormatter(){
        return new MessageFormatterImpl();
    }

    @Bean
    @Scope("singleton")
    public LootBox getLootBox() {
        return new LootBoxImpl();
    }

    @Bean
    @Scope("singleton")
    public CardService getCardService() {
        return new CardServiceImpl();
    }

    @Bean
    public RegisterCommand getRegisterCommand(){
        return new RegisterCommand();
    }
}
