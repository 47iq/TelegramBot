package data;

import game.entity.Card;
import game.entity.Task;

import java.util.List;

public interface TaskDAO {

    List<Task> getAll();

    Task getEntityById(long UID);

    Task update(Task task);

    boolean delete(Task task);

    boolean create(Task task);
}
