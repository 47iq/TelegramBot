package game.service;

import game.entity.Card;

public interface BattleXpCalculator {
    long calcXp(Card winner, Card loser);
}
