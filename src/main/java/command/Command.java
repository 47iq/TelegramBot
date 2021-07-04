package command;

import util.AnswerDTO;
import util.CommandDTO;

public interface Command {
    AnswerDTO execute(CommandDTO commandDTO);
}
