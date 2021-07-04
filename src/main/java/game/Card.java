package game;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.ImageIdentifier;

import javax.persistence.*;
import java.io.File;

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
    @Column(name = "attack")
    double attack;
    @Column(name = "defence")
    double defence;
    @Column(name = "type")
    CardType type;
    @Id
    @Basic(optional = false)
    @Column(name = "uid", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long UID;
    @Column(name = "owner")
    String owner;

    public Card() {

    }

    public Card(CardName name, CardType type, User user) {
        this.name = name;
        this.type = type;
        this.owner = user.getUID();
        level = 1;
        health = (1 + Math.random()*0.5)*type.getMultiplier()*name.gerMultiplier();
        attack = (1 + Math.random()*0.5)*type.getMultiplier()*name.gerMultiplier();
        defence = (1 + Math.random()*0.5)*type.getMultiplier()*name.gerMultiplier();
    }

    public Card levelUp() {
        level++;
        health = (1 + Math.random()*0.25)*type.getMultiplier()*health*name.gerMultiplier();
        attack = (1 + Math.random()*0.25)*type.getMultiplier()*attack*name.gerMultiplier();
        defence = (1 + Math.random()*0.25)*type.getMultiplier()*defence*name.gerMultiplier();
        return this;
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
}
