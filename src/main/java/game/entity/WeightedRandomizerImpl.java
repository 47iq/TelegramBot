package game.entity;

import java.util.*;

public class WeightedRandomizerImpl<T> implements WeightedRandomizer<T> {

    NavigableMap<Double, T> weights = new TreeMap<>();
    Random random = new Random();
    double totalWeight;

    public WeightedRandomizerImpl(Map<T, Double> weights) {
        double currentVal = 0;
        for(T item: weights.keySet()) {
            currentVal += weights.get(item);
            this.weights.put(currentVal, item);
        }
        totalWeight = currentVal;
    }

    @Override
    public T getRandom() {
        double value = random.nextDouble() * totalWeight;
        return weights.higherEntry(value).getValue();
    }
}
