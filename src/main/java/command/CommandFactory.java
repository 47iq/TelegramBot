package command;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;

/**
 * Interface of a command factory.
 */
public interface CommandFactory {

    /**
     * Method that parses and executes command object from text command.
     *
     * @param commandDTO request meta data.
     * @return answer on a request.
     * @see Command
     */

    AnswerDTO execute(CommandDTO commandDTO);
}
