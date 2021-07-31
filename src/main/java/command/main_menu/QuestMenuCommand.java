package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;

public class QuestMenuCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("quest_menu"), KeyboardType.QUEST_MENU, null, null, commandDTO.getUser(), true);
    }
}
