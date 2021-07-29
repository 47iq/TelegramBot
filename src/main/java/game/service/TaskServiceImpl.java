package game.service;

import command.service_command.OpenTaskBoxCommand;
import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.TaskDAO;
import game.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageFormatter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDAO taskDAO;
    @Autowired
    WeightedRandomizer<TaskType> taskTypeWeightedRandomizer;
    @Autowired
    WeightedRandomizer<RewardType> rewardTypeWeightedRandomizer;
    @Autowired
    WeightedRandomizer<Integer> taskLevelWeightedRandomizer;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserService userService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    OpenTaskBoxCommand taskBoxCommand;
    @Autowired
    UserBalanceService userBalanceService;
    @Autowired
    AchievementService achievementService;

    List<Task> tasks;

    @PostConstruct
    void init() {
        tasks = new ArrayList<>(taskDAO.getAll());
    }

    @Override
    public Task getTask(User user) {
        TaskType taskType = taskTypeWeightedRandomizer.getRandom();
        if(!find(user, taskType).isEmpty())
            return null;
        RewardType rewardType = rewardTypeWeightedRandomizer.getRandom();
        int level = taskLevelWeightedRandomizer.getRandom();
        if(rewardType.equals(RewardType.LOOT_BOX))
            level = 5;
        Task task = new Task(taskType, rewardType, level, user);
        tasks.add(task);
        taskDAO.create(task);
        return task;
    }

    @Override
    public List<Task> getAll(User user) {
        return taskDAO.getAll().stream().filter(x -> x.getUserUID().equals(user.getUID())).collect(Collectors.toList());
    }

    private List<Task> find(User user, TaskType taskType) {
        return getAll(user)
                .stream()
                .filter(x -> x.getTaskType().equals(taskType))
                .collect(Collectors.toList());
    }

    @Override
    public void addProgress(User user, TaskType taskType, long value) {
        List<Task> toDelete = new ArrayList<>();
        List<Task> tasks = find(user, taskType);
        tasks.forEach(task -> {
            if(task == null)
                return;
            task.setProgress(task.getProgress() + value);
            toDelete.add(task);
        });
        toDelete.forEach(task -> {
            if(task.getProgress() >= task.getNeeded())
                completeTask(task);
        });
    }

    private void completeTask(Task task) {
        taskDAO.delete(task);
        tasks.remove(task);
        User user = userService.getUserData(new User(task.getUserUID(), 0));
        achievementService.addProgress(user, AchievementType.TASKS);
        switch (task.getRewardType()) {
            case LOOT_BOX -> completeLootBoxTask(task, user);
            case HEAL -> completeHealTask(task, user);
            case MONEY -> completeMoneyTask(task, user);
        }
    }

    private void completeMoneyTask(Task task, User user) {
        long reward = task.getReward();
        userService.addTokens(user, reward);
        notifyCompleted(task, user);
    }

    private void completeHealTask(Task task, User user) {
        long reward = task.getReward();
        for(int i = 0; i < reward; i++)
            userService.addHeal(user);
        notifyCompleted(task, user);
    }

    private void completeLootBoxTask(Task task, User user) {
        AnswerDTO answerDTO = taskBoxCommand.execute(new CommandDTO(user, ""));
        String message = messageFormatter.getTaskCompleteMessage(task);
        answerDTO.appendToBeginning(message + "\n\n");
        notificationService.notify(user, answerDTO);
    }

    private void notifyCompleted(Task task, User user) {
        AnswerDTO answerDTO = new AnswerDTO(true, messageFormatter.getTaskCompleteMessage(task), KeyboardType.NONE, null, null, user, true);
        notificationService.notify(user, answerDTO);
    }
}
