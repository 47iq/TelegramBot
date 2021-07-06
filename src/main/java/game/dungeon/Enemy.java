package game.dungeon;

import game.entity.Card;

public class Enemy {
    private final EnemyType enemyType;
    private final Card enemyCard;
    private final long award;

    public Enemy() {
        this.enemyType = EnemyType.getRandom();
        this.enemyCard = enemyType.getStats();
        this.award  = enemyType.getAward();
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public Card getEnemyCard() {
        return enemyCard;
    }

    public long getAward() {
        return award;
    }
}
