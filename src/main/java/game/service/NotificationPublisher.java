package game.service;

import communication.util.AnswerDTO;
import game.entity.User;

public interface NotificationPublisher {
    void subscribe(EventType eventType, User user);
    void unsubscribe(EventType eventType, User user);
    void notify(EventType eventType, AnswerDTO answerDTO);
    boolean isSubscribed(User user, EventType eventType);
}
