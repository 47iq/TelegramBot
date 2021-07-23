package data;

import java.util.List;

public interface AchievementDAO {

    List<Achievement> getAll();

    Achievement getEntityById(String UID);

    Achievement update(Achievement achievement);

    boolean delete(Achievement achievement);

    boolean create(Achievement achievement);
}
