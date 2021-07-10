package game.service;

import game.entity.Card;

/**
 * Interface that provides price calculating methods
 */

public interface PriceCalculator {

    /**
     * Method that calculates card price
     *
     * @param card card
     * @return card price
     */

    long calculatePrice(Card card);
}
