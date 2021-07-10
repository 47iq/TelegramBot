package game.dungeon;

import data.CardServiceImpl;
import game.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PVE enemy class
 */

public class Enemy {

    private final EnemyType enemyType;

    private final Card enemyCard;

    private final long award;

    private static final Logger LOGGER = LogManager.getLogger(Enemy.class);

    /**
     * Constructor that generates a random enemy
     */

    public Enemy() {
        this.enemyType = EnemyType.getRandom();
        LOGGER.info(enemyType.name() + " has been created.");
        this.enemyCard = enemyType.getStats();
        this.award = enemyType.getAward();
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
