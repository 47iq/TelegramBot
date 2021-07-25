package game.service;

import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import data.User;
import data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class BattleNotificationPublisher implements NotificationPublisher {
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserService userService;

    Map<EventType, Set<User>> subscribers = new HashMap<>();

    public BattleNotificationPublisher() {
        Arrays.stream(EventType.values()).forEach(x -> subscribers.put(x, new HashSet<>()));
    }

    @PostConstruct
    void init() {
        try {
            userService.getAllUsers().forEach(x -> {
                if(x.getIsSubscribedToBattle() != null && x.getIsSubscribedToBattle())
                    subscribers.get(EventType.BATTLE_ENEMY).add(x);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(EventType eventType, User user) {
        subscribers.get(eventType).add(user);
        userService.subscribe(eventType, user);
    }

    @Override
    public void unsubscribe(EventType eventType, User user) {
        subscribers.get(eventType).remove(user);
        userService.unsubscribe(eventType, user);
    }

    @Override
    public void notify(EventType eventType, AnswerDTO answerDTO) {
        subscribers.get(eventType).forEach(x -> notificationService.notify(x, answerDTO));
    }

    @Override
    public boolean isSubscribed(User user, EventType eventType) {
        return subscribers.get(eventType).contains(user);
    }
}
