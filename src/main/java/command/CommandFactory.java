package command;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;

public interface CommandFactory {
    AnswerDTO execute(CommandDTO commandDTO);
}
