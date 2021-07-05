package data;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "uid", unique = true)
    private String UID;

    @Column(name = "chat_id")
    private long chatID;

    @Column(name = "tokens")
    private long tokens;

    @Column(name = "redeem_time")
    private LocalDateTime lastTokensRedeemed;

    @Column(name = "heal")
    private long healCount;

    @Column(name = "boost")
    private long boostCount;

    @Column(name = "battles")
    private int totalBattles;

    @Column(name = "wins")
    private int totalWins;

    public User(String username, long chatId) {
        this.UID = username;
        this.chatID = chatId;
        tokens = Long.parseLong(ResourceBundle.getBundle("settings").getString("INITIAL_TOKENS"));
        healCount = 1;
        boostCount = 1;
        lastTokensRedeemed = LocalDateTime.now(ZoneId.systemDefault());
        totalBattles = 0;
        totalWins = 0;
    }

    public User() {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public LocalDateTime getLastTokensRedeemed() {
        return lastTokensRedeemed;
    }

    public void setLastTokensRedeemed(LocalDateTime lastTokensRedeemed) {
        this.lastTokensRedeemed = lastTokensRedeemed;
    }

    public long getHealCount() {
        return healCount;
    }

    public void setHealCount(long healCount) {
        this.healCount = healCount;
    }

    public long getBoostCount() {
        return boostCount;
    }

    public void setBoostCount(long boostCount) {
        this.boostCount = boostCount;
    }

    public void addWin() {
        totalWins++;
    }

    public void addBattle() {
        totalBattles++;
    }

    public int getTotalBattles() {
        return totalBattles;
    }

    public void setTotalBattles(int totalBattles) {
        this.totalBattles = totalBattles;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }
}
