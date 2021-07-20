package game.service;

/**
 * Class that provides weighted random methods
 *
 * @param <T> type of random value
 */

public interface WeightedRandomizer<T> {

    /**
     * Method that returns weighted random value
     *
     * @return weighted random value
     */

    T getRandom();
}
