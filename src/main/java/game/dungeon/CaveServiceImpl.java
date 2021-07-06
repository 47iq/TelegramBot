package game.dungeon;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;
import data.CardService;
import data.User;
import data.UserService;
import game.entity.Card;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CaveServiceImpl implements CaveService{
    @Autowired
    private CardService cardService;
    @Autowired
    private MessageFormatter messageFormatter;
    @Autowired
    private UserService userService;
    @Autowired
    private BattleService battleService;

    static Map<User, Card> cardMap = new HashMap<>();

    @Override
    public AnswerDTO enterCaves(CommandDTO commandDTO, Card card) {
        if(cardMap.containsKey(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("err_incaves"),  KeyboardType.LEAF, null,  null);
        cardMap.put(commandDTO.getUser(), card);
        return enterNextCave(commandDTO);
    }

    @Override
    public AnswerDTO leaveCaves(CommandDTO commandDTO) {
        cardMap.remove(commandDTO.getUser());
        return new AnswerDTO(true,  null,  KeyboardType.CLASSIC,  null,  null);
    }

    @Override
    public AnswerDTO enterNextCave(CommandDTO commandDTO) {
        if(!cardMap.containsKey(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("err_notincaves"),  KeyboardType.LEAF, null,  null);;
        if(cardMap.get(commandDTO.getUser()).getHealth() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nohealth"), KeyboardType.LEAF, null, null);
        return getCave().enterThisCave(commandDTO,  cardMap.get(commandDTO.getUser()), battleService, messageFormatter, cardService, userService);
    }

    private Cave getCave() {
        int rnd = (int) (Math.random() * 100);
        if(rnd < 20)
            return new RobberyCave();
        else if(rnd  < 40)
            return new TrapCave();
        else if(rnd  < 50)
            return new LootCave();
        else if(rnd  < 60)
            return new HealCave();
        else
            return new BattleCave();
    }
}
