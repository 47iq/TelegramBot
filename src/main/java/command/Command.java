package command;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Interface for commands of the app
 */

public interface Command {

    /**
     * Method that executes a command.
     *
     * @param commandDTO request meta data.
     * @return answer on a request.
     */

    AnswerDTO execute(CommandDTO commandDTO);
}
