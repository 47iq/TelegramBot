package game.battle;

import game.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class BattleXpCalculatorImpl implements BattleXpCalculator{
    @Override
    public long calcXp(Card winner, Card loser) {
        long xp = 300;
        if(loser.getMaxHealth() <= 0)
            return xp + 200;
        if(winner.getMaxHealth() < loser.getMaxHealth())
            xp += 1000;
        if(winner.getAttack() < loser.getAttack())
            xp += 1000;
        if(winner.getDefence() < loser.getDefence())
            xp += 500;
        if(loser.getOwner() != null)
            xp += 4000;
        return xp;
    }
}
