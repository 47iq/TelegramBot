package game.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "achievements")
public class Achievement {

    @Id
    @Column(name = "owner", unique = true)
    String owner;
    @Column(name = "caves")
    long cavesNumber;
    @Column(name = "battles")
    long battlesNumber;
    @Column(name = "box_caves")
    long boxCavesNumber;
    @Column(name = "total_cards")
    long totalCards;
    @Column(name = "money_spent")
    Long moneySpent;
    @Column(name = "tasks_done")
    Long tasksDone;
    @Column(name = "card_level_num")
    Long cardLeveledUp;
    @Column(name = "pvp_wins")
    Long pvpWins;

    public Achievement(String owner) {
        this.owner = owner;
        cavesNumber = 0;
        battlesNumber = 0;
        boxCavesNumber = 0;
        totalCards = 0;
        moneySpent = 0L;
        tasksDone = 0L;
        cardLeveledUp = 0L;
        pvpWins = 0L;
    }

    public Achievement() {

    }

    public long getPvpWins() {
        return pvpWins == null ? 0 : pvpWins ;
    }

    public void setPvpWins(Long pvpWins) {
        this.pvpWins = (pvpWins == null ? 0 : pvpWins);
    }

    public long getCardLeveledUp() {
        return cardLeveledUp == null ? 0 : cardLeveledUp;
    }

    public void setCardLeveledUp(Long cardLeveledUp) {
        this.cardLeveledUp = (cardLeveledUp == null ? 0 : cardLeveledUp);
    }

    public long getTasksDone() {
        return tasksDone == null ? 0 : tasksDone;
    }

    public void setTasksDone(Long tasksDone) {
        this.tasksDone =  (tasksDone == null ? 0 : tasksDone);
    }

    public long getMoneySpent() {
        return moneySpent == null ? 0 : moneySpent;
    }

    public void setMoneySpent(Long moneySpent) {
        this.moneySpent = (moneySpent == null ? 0 : moneySpent);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getCavesNumber() {
        return cavesNumber;
    }

    public void setCavesNumber(long cavesNumber) {
        this.cavesNumber = cavesNumber;
    }

    public long getBattlesNumber() {
        return battlesNumber;
    }

    public void setBattlesNumber(long battlesNumber) {
        this.battlesNumber = battlesNumber;
    }

    public long getBoxCavesNumber() {
        return boxCavesNumber;
    }

    public void setBoxCavesNumber(long boxCavesNumber) {
        this.boxCavesNumber = boxCavesNumber;
    }

    public long getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(long totalCards) {
        this.totalCards = totalCards;
    }
}
