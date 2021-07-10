package game.service;

import game.entity.Card;

/**
 * Interface that provides xp calculating methods
 */

public interface BattleXpCalculator {
    /**
     * Method that calculates battle xp for a winning card
     *
     * @param winner winner card
     * @param loser  loser card
     * @return battle xp
     */

    long calcXp(Card winner, Card loser);
}
