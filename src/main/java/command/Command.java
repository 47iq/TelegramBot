package command;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;

public interface Command {
    AnswerDTO execute(CommandDTO commandDTO);
}
