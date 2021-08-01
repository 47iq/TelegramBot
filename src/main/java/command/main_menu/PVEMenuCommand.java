package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;

public class PVEMenuCommand implements Command {
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("pve_menu"), KeyboardType.PVE_MENU, null, null, commandDTO.getUser(), true);
    }
}
