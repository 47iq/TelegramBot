package game.quest;

public class QuestState {
    private long UID;
    private long stage;
    private String userUID;
    private long cardUID;
    private QuestType type;
    private boolean isRunning;
    private long step;

    public QuestState(String userUID, long cardUID, QuestType type) {
        this.userUID = userUID;
        this.cardUID = cardUID;
        this.type = type;
        stage = 0;
        step = 0;
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
}
