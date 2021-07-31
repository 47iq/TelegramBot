package game.quest;

import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import game.battle.PVEBattleResult;
import game.dungeon.Enemy;
import game.dungeon.EnemyType;
import game.entity.Card;
import game.entity.User;
import game.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageBundle;
import util.MessageFormatter;

import java.util.Map;
import java.util.NavigableSet;

@Component
public class FirstQuest implements Quest {

    @Autowired
    BattleService battleService;

    @Autowired
    MessageFormatter messageFormatter;

    @Autowired
    private Map<Long, QuestStepAction> questStageActionMap;

    @Autowired
    private Map<Long, EnemyType> questEnemyTypeMap;

    @Autowired
    private NavigableSet<Long> stages;

    private final long MAX_STEP = Long.parseLong(MessageBundle.getSetting("FIRST_QUEST_MAX_STEP"));

    @Override
    public AnswerDTO continueQuest(QuestState questState, Card card, User user) {
        long step = questState.getStage();
        if(questState.getIsRunning())
            step = questState.getStep();
        return switch (questStageActionMap.get(step)) {
            case BATTLE -> battle(questState, card, user, step);
            case SHOP -> shop(questState, card, user, step);
            case MESSAGE -> message(questState, card, user, step);
        };
    }

    private AnswerDTO message(QuestState questState, Card card, User user, long step) {
        AnswerDTO answerDTO = new AnswerDTO(true, messageFormatter.getQuestMessage(QuestType.QUEST_1, step),
                KeyboardType.QUEST, null, null, user, true);
        step++;
        return prepareToSend(questState, step, answerDTO);
    }

    private AnswerDTO shop(QuestState questState, Card card, User user, long step) {
        AnswerDTO answerDTO = new AnswerDTO(true, messageFormatter.getQuestShopMessage(QuestType.QUEST_1, step),
                KeyboardType.QUEST_SHOP, null, null, user, true);
        step++;
        return prepareToSend(questState, step, answerDTO);
    }

    private AnswerDTO battle(QuestState questState, Card card, User user, long step) {
        Enemy enemy = new Enemy(questEnemyTypeMap.get(step));
        PVEBattleResult result = battleService.battleQuestEnemy(user, enemy, card);
        AnswerDTO answerDTO = result.getResultMessage();
        if(result.isHasWon()) {
            step++;
        } else {
            questState.setIsRunning(false);
        }
        return prepareToSend(questState, step, answerDTO);
    }

    private AnswerDTO prepareToSend(QuestState questState, long step, AnswerDTO answerDTO) {
        if(!answerDTO.getKeyboard().equals(KeyboardType.QUEST_LEAF)) {
            Long stage = stages.ceiling(step);
            if (stage != null && stage == step)
                questState.setStage(step);
            if (step == MAX_STEP) {
                answerDTO.setKeyboardType(KeyboardType.QUEST_LEAF);
                return answerDTO;
            }
            switch (questStageActionMap.get(step)) {
                case SHOP -> answerDTO.setKeyboardType(KeyboardType.QUEST_SHOP);
                case BATTLE, MESSAGE -> answerDTO.setKeyboardType(KeyboardType.QUEST);
            }
        }
        return answerDTO;
    }
}
