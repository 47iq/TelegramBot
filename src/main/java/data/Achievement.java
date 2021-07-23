package data;

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

    public Achievement(String owner) {
        this.owner = owner;
        cavesNumber = 0;
        battlesNumber = 0;
        boxCavesNumber = 0;
        totalCards = 0;
    }

    public Achievement() {

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
