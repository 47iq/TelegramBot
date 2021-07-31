package data;

import game.quest.QuestState;

import java.util.List;

public interface QuestDAO {
    List<QuestState> getAll();

    QuestState getEntityById(long UID);

    QuestState update(QuestState task);

    boolean delete(QuestState task);

    boolean create(QuestState task);
}
