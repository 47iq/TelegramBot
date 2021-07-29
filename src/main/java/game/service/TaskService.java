package game.service;

import game.entity.User;
import game.entity.Task;
import game.entity.TaskType;

import java.util.List;

public interface TaskService {
    Task getTask(User user);
    List<Task> getAll(User user);
    void addProgress(User user, TaskType taskType, long value);
}
