package game;

public class PriceCalculatorImpl implements PriceCalculator{
    @Override
    public long calculatePrice(Card card) {
        return (long) ((card.getMaxHealth()*card.getDefence()*card.getAttack())*card.getType().getMultiplier()*card.getName().gerMultiplier()*7/card.getLevel());
    }
}
