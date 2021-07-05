package game;

import data.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface BattleService {
    boolean isBattling(User user);
    void startSearch(User user, Card card,TelegramLongPollingBot bot);
    void leaveSearch(User user);
}
