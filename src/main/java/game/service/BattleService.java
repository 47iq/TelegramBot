package game.service;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.User;
import game.dungeon.Enemy;
import game.entity.Card;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Interface that provides methods for card battles
 */

public interface BattleService {

    /**
     * Method that shows whether user is in battle search now or not
     * @param user user
     * @return true - if user is in search, false - if not
     */

    boolean isBattling(User user);

    /**
     * Method that starts battle search
     * @param user user that starts search
     * @param card card that starts search
     * @param bot bot for notification
     */

    void startSearch(User user, Card card, TelegramLongPollingBot bot);

    /**
     * Method that makes user leave search
     * @param user user
     */

    void leaveSearch(User user);

    /**
     * Method for PVE battles
     * @param commandDTO command DTO
     * @param enemy enemy
     * @param card user's card
     * @return answer on a request
     */

    AnswerDTO battleEnemy(CommandDTO commandDTO, Enemy enemy, Card card);
}
