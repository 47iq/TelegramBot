package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;
import communication.util.MessageFormatter;
import data.*;
import game.entity.Card;
import game.service.BattleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CaveServiceImpl implements CaveService {
    @Autowired
    private CardService cardService;
    @Autowired
    private MessageFormatter messageFormatter;
    @Autowired
    private UserService userService;
    @Autowired
    private BattleService battleService;
    @Autowired
    private OpenSuperRareBoxCommand command;

    private static final Logger LOGGER = LogManager.getLogger(CaveServiceImpl.class);

    static Map<User, Card> cardMap = new HashMap<>();

    @Override
    public AnswerDTO enterCaves(CommandDTO commandDTO, Card card) {
        if (cardMap.containsKey(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("err_incaves"), KeyboardType.LEAF, null, null);
        cardMap.put(commandDTO.getUser(), card);
        return enterNextCave(commandDTO);
    }

    @Override
    public AnswerDTO leaveCaves(CommandDTO commandDTO) {
        cardMap.remove(commandDTO.getUser());
        return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null);
    }

    @Override
    public AnswerDTO enterNextCave(CommandDTO commandDTO) {
        if (!cardMap.containsKey(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("err_notincaves"), KeyboardType.LEAF, null, null);
        if (cardMap.get(commandDTO.getUser()).getHealth() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nohealth"), KeyboardType.LEAF, null, null);
        Cave cave = getCave();
        if (cave instanceof LevelUpCave && cardMap.get(commandDTO.getUser()).getLevel() >= 10)
            cave = new ArmorCave();
        LOGGER.info(commandDTO.getUser().getUID() + " has entered cave: " + cave.getClass());
        return cave.enterThisCave(commandDTO, cardMap.get(commandDTO.getUser()), battleService, messageFormatter, cardService, userService, command);
    }

    /**
     * Random cave generator
     *
     * @return random cave
     */

    public Cave getCave() {
        double rnd = (Math.random() * 100);
        //todo
        System.out.println("Cave: " + rnd);
        if (rnd < 10)
            return new RobberyCave();
        else if (rnd < 20)
            return new TrapCave();
        else if (rnd < 30)
            return new LootCave();
        else if (rnd < 40)
            return new HealCave();
        else if (rnd < 97)
            return new BattleCave();
        else if (rnd < 98)
            return new WeaponCave();
        else if (rnd < 99)
            return new ArmorCave();
        else if (rnd < 99.75)
            return new LevelUpCave();
        else
            return new LootBoxCave();
    }
}
