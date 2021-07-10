package game.dungeon;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.Card;

/**
 * Interface that provides methods for caves
 */

public interface CaveService {

    /**
     * Method that prepares caves for user and enters the first cave
     *
     * @param commandDTO command DTO
     * @param card       user's card
     * @return answer containing a first cave entrance answer
     * @see #enterNextCave(CommandDTO)
     */

    AnswerDTO enterCaves(CommandDTO commandDTO, Card card);

    /**
     * Method that leaves caves for a user
     *
     * @param commandDTO command DTO
     * @return answer
     */

    AnswerDTO leaveCaves(CommandDTO commandDTO);

    /**
     * Method that makes user enter a random  cave
     *
     * @param commandDTO command DTO
     * @return answer
     */

    AnswerDTO enterNextCave(CommandDTO commandDTO);
}
