package command;

import util.AnswerDTO;
import util.CommandDTO;

public interface CommandFactory {
    AnswerDTO execute(CommandDTO commandDTO);
}
