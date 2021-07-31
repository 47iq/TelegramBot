package game.quest;

import java.util.Objects;

public class QuestState {
    private long UID;
    private Long stage;
    private String userUID;
    private long cardUID;
    private QuestType type;
    private boolean isRunning;
    private Long step;
    private long deaths;

    public QuestState(String userUID, long cardUID, QuestType type) {
        this.userUID = userUID;
        this.cardUID = cardUID;
        this.type = type;
        isRunning = true;
        stage = 1L;
        step = 1L;
        deaths = 0;
    }

    public QuestState() {
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getCardUID() {
        return cardUID;
    }

    public void setCardUID(long cardUID) {
        this.cardUID = cardUID;
    }

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    public long getStage() {
        return stage;
    }

    public void setStage(long stage) {
        this.stage = stage;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public QuestType getType() {
        return type;
    }

    public void setType(QuestType type) {
        this.type = type;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean running) {
        isRunning = running;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestState that = (QuestState) o;
        return UID == that.UID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID);
    }
}
