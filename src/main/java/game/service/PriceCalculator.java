package game.service;

import game.entity.Card;

public interface PriceCalculator {
    long calculatePrice(Card card);
}
