package game.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Component
@Entity(name = "tasks")
public class Task {
    @Id
    @Basic(optional = false)
    @Column(name = "uid", unique = true)
    long uid;
    @Column(name = "task_type")
    TaskType taskType;
    @Column(name = "reward_type")
    RewardType rewardType;
    @Column(name = "user_uid")
    String userUID;
    @Column(name = "progress")
    long progress;
    @Column(name = "needed")
    long needed;
    @Column(name = "reward")
    long reward;

    public Task(TaskType taskType, RewardType rewardType, int level, User user) {
        this.taskType = taskType;
        this.rewardType = rewardType;
        this.userUID = user.getUID();
        needed = taskType.getValueNeeded(level);
        reward = rewardType.getAmount(level);
    }

    public Task() {

    }

    public long getReward() {
        return reward;
    }

    public void setReward(long reward) {
        this.reward = reward;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getNeeded() {
        return needed;
    }

    public void setNeeded(long needed) {
        this.needed = needed;
    }

}
