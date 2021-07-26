package game.marketplace;

import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Component
@Entity(name = "marketplace")
public class Merchandise {
    @Id
    @Basic(optional = false)
    @Column(name = "uid", unique = true)
    private long cardUID;

    @Column(name = "cost")
    private long cost;

    @Column(name = "listing_time")
    private LocalDateTime listingTime;

    public Merchandise() {
    }

    public Merchandise(long cardUID, long cost) {
        this.cardUID = cardUID;
        this.cost = cost;
        this.listingTime = LocalDateTime.now();
    }

    public long getCardUID() {
        return cardUID;
    }

    public void setCardUID(long cardUID) {
        this.cardUID = cardUID;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public LocalDateTime getListingTime() {
        return listingTime;
    }

    public void setListingTime(LocalDateTime listingTime) {
        this.listingTime = listingTime;
    }
}
