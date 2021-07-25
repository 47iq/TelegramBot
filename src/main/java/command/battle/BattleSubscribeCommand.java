package command.battle;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.service.EventType;
import game.service.NotificationPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;

@Component
public class BattleSubscribeCommand implements Command {
    @Autowired
    NotificationPublisher notificationPublisher;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        notificationPublisher.subscribe(EventType.BATTLE_ENEMY, commandDTO.getUser());
        return new AnswerDTO(true, MessageBundle.getMessage("info_success"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
    }
}