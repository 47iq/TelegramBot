package command.main_menu;

import command.Command;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;

/**
 * Command, which displays a dungeon menu.
 * Syntax: /dungeon_menu
 */


public class DungeonMenuCommand implements Command {

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, MessageBundle.getMessage("dungeon_intro"), KeyboardType.DUNGEON_MENU, null, null, commandDTO.getUser());
    }
}
