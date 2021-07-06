package game.dungeon;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.Card;

public interface CaveService {
    AnswerDTO enterCaves(CommandDTO commandDTO, Card card);
    AnswerDTO leaveCaves(CommandDTO commandDTO);
    AnswerDTO enterNextCave(CommandDTO commandDTO);
}
