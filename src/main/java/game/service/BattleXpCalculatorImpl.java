package game.service;

import game.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class BattleXpCalculatorImpl implements BattleXpCalculator{
    @Override
    public long calcXp(Card winner, Card loser) {
        long xp = 500;
        if(loser.getMaxHealth() <= 0)
            return xp + 500;
        if(winner.getMaxHealth() < loser.getMaxHealth())
            xp += 1500;
        if(winner.getAttack() < loser.getAttack())
            xp += 1500;
        if(winner.getDefence() < loser.getDefence())
            xp += 1000;
        if(loser.getOwner() != null)
            xp += 4000;
        return xp;
    }
}
