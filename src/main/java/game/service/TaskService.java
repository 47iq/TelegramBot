package game.service;

import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.User;
import game.entity.RewardType;
import game.entity.Task;
import game.entity.TaskType;

import java.util.List;

public interface TaskService {
    Task getTask(User user);
    List<Task> getAll(User user);
    void addProgress(User user, TaskType taskType, long value);
}
