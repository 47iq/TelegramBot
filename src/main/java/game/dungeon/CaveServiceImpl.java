package game.dungeon;

import command.service_command.OpenSuperRareBoxCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.TaskType;
import game.service.AchievementService;
import game.service.OccupationService;
import game.service.TaskService;
import util.MessageBundle;
import util.MessageFormatter;
import data.*;
import game.entity.Card;
import game.service.WeightedRandomizer;
import game.service.BattleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
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
    @Autowired
    @Qualifier("cave_random")
    private WeightedRandomizer<Class<? extends Cave>> caveWeightedRandomizer;
    @Autowired
    @Qualifier("enemy_random")
    private WeightedRandomizer<EnemyType> enemyWeightedRandomizer;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    OccupationService occupationService;
    @Autowired
    TaskService taskService;

    private static final Logger LOGGER = LogManager.getLogger(CaveServiceImpl.class);

    private static final Map<User, Card> cardMap = new HashMap<>();

    @Override
    public AnswerDTO enterCaves(CommandDTO commandDTO, Card card) {
        User user = commandDTO.getUser();
        cardMap.remove(user);
        cardMap.put(user, card);
        return enterNextCave(commandDTO);
    }

    @Override
    public AnswerDTO leaveCaves(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        cardMap.remove(commandDTO.getUser());
        return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null, user, true);
    }

    @Override
    public AnswerDTO enterNextCave(CommandDTO commandDTO) {
        User user = commandDTO.getUser();
        if(occupationService.isOccupied(cardMap.get(user)))
            return new AnswerDTO(false, MessageBundle.getMessage("err_inbattle"), KeyboardType.LEAF, null, null, commandDTO.getUser(), true);
        if (!cardMap.containsKey(commandDTO.getUser()))
            return new AnswerDTO(true, MessageBundle.getMessage("err_notincaves"), KeyboardType.LEAF, null, null, user, true);
        if (cardMap.get(commandDTO.getUser()).getHealth() <= 0)
            return new AnswerDTO(false, MessageBundle.getMessage("err_nohealth"), KeyboardType.LEAF, null, null, user, true);
        Cave cave = getCave();
        if (cave instanceof LevelUpCave && cardMap.get(commandDTO.getUser()).getLevel() >= 10)
            cave = new ArmorCave();
        LOGGER.info(commandDTO.getUser().getUID() + " has entered cave: " + cave.getClass());
        achievementService.addCave(user);
        taskService.addProgress(user, TaskType.CAVE, 1);
        AnswerDTO answerDTO = cave.enterThisCave(commandDTO, cardMap.get(commandDTO.getUser()), battleService, messageFormatter, cardService, userService, command, enemyWeightedRandomizer, taskService);
        if(answerDTO != null)
            return answerDTO;
        return (new BattleCave()).enterThisCave(commandDTO, cardMap.get(commandDTO.getUser()), battleService, messageFormatter, cardService, userService, command, enemyWeightedRandomizer, taskService);
    }

    /**
     * Random cave generator
     *
     * @return random cave
     */

    public Cave getCave() {
        try {
            return caveWeightedRandomizer.getRandom().getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Cant use reflection: " + e.getClass());
            return new BattleCave();
        }
    }

    @Override
    public Card getCard(User user) {
        return cardMap.get(user);
    }
}
