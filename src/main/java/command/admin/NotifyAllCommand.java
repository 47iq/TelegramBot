package command.admin;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyAllCommand implements Command {
    @Autowired
    NotificationService notificationService;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        AnswerDTO answerDTO = new AnswerDTO(true, commandDTO.getArg(), KeyboardType.LEAF, null, null);
        answerDTO.setBot(commandDTO.getBot());
        notificationService.notifyAll(answerDTO);
        return new AnswerDTO(true, MessageBundle.getMessage("info_success"), KeyboardType.LEAF, null, null);
    }
}
