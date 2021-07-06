package game.service;

import game.entity.Card;
import game.service.PriceCalculator;

public class PriceCalculatorImpl implements PriceCalculator {
    @Override
    public long calculatePrice(Card card) {
        return (long) ((card.getMaxHealth()*card.getDefence()*card.getAttack())*card.getType().getMultiplier()*card.getName().gerMultiplier()*14/Math.pow(card.getLevel() + 3, 2));
    }
}
