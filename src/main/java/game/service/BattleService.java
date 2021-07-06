package game.service;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.User;
import game.dungeon.Enemy;
import game.entity.Card;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface BattleService {
    boolean isBattling(User user);
    void startSearch(User user, Card card, TelegramLongPollingBot bot);
    void leaveSearch(User user);
    AnswerDTO battleEnemy(CommandDTO commandDTO, Enemy enemy, Card card);
}
