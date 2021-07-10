package game.entity;

import communication.util.MessageBundle;
import data.User;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Card class
 */

@Component
@Entity(name = "cards")
public class Card {

    @Column(name = "name")
    CardName name;
    File image;
    @Column(name = "level")
    int level;
    @Column(name = "health")
    double health;
    @Column(name = "max_health")
    double maxHealth;
    @Column(name = "attack")
    double attack;
    @Column(name = "defence")
    double defence;
    @Column(name = "type")
    CardType type;
    @Column(name = "xp")
    long xp;
    @Id
    @Basic(optional = false)
    @Column(name = "uid", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long UID;
    @Column(name = "owner")
    String owner;

    public Card() {

    }

    /**
     * Constructor for loot box cards
     *
     * @param name name of card
     * @param type rarity of card
     * @param user owner
     */

    public Card(CardName name, CardType type, User user) {
        this.name = name;
        this.type = type;
        this.owner = user.getUID();
        level = 1;
        health = 3 * (1 + 3 * Math.random()) * type.getMultiplier() * name.gerMultiplier();
        maxHealth = health;
        attack = (1 + 3 * Math.random()) * type.getMultiplier() * name.gerMultiplier();
        defence = (1 + 3 * Math.random()) * type.getMultiplier() * name.gerMultiplier();
    }

    /**
     * Constructor for enemy cards for PVE
     *
     * @param name    name of card
     * @param health  health
     * @param attack  attack
     * @param defence defence
     * @see game.dungeon.Enemy
     */

    public Card(@Nullable CardName name, double health, double attack, double defence) {
        this.name = name;
        this.owner = null;
        this.maxHealth = health;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
    }

    /**
     * Increases cards level and boosts its stats randomly
     */

    public void levelUp() {
        level++;
        maxHealth += 3 * (1 + Math.random() * 0.25) * type.getMultiplier() * name.gerMultiplier();
        health = maxHealth;
        attack += (1 + Math.random() * 0.25) * type.getMultiplier() * name.gerMultiplier();
        defence += (1 + Math.random() * 0.25) * type.getMultiplier() * name.gerMultiplier();
    }

    public CardName getName() {
        return name;
    }


    public int getLevel() {
        return level;
    }

    public double getHealth() {
        return health;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    public CardType getType() {
        return type;
    }

    public Long getUID() {
        return UID;
    }

    public String getOwner() {
        return owner;
    }

    public void setName(CardName name) {
        this.name = name;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setUID(Long UID) {
        this.UID = UID;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void heal() {
        health = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }


    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    /**
     * Method that returns xp needed to level up
     * @return xp needed to level up
     */

    public Long getNextLevelXp() {
        if (level <= 19)
            return calcNextLevelXp() - xp;
        else
            return null;
    }

    /**
     * Method that returns an absolute value of xp needed to reach next level
     * @return xp needed to reach next level
     */

    public Long calcNextLevelXp() {
        long basic = Long.parseLong(MessageBundle.getSetting("LEVEL_XP"));
        if (level <= 5)
            return basic * this.level;
        else if (level <= 10)
            return basic * 2 * this.level;
        else if (level <= 15)
            return basic * 4 * this.level;
        else if (level <= 17)
            return basic * 8 * this.level;
        else if (level <= 18)
            return basic * 16 * this.level;
        else if (level <= 19)
            return basic * 32 * this.level;
        else
            return null;
    }
}
