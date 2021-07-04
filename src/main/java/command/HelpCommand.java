package command;

import keyboard.KeyboardType;
import util.AnswerDTO;
import util.CommandDTO;
import util.MessageBundle;

public class HelpCommand implements Command{
    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null);
    }
}
