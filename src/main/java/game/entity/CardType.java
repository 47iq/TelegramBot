package game.entity;

/**
 * Class that represents rarity of a card
 */

public enum CardType {
    BASIC,
    RARE{
        public double getMultiplier() {
            return 1.25;
        }
    },
    EPIC{
        public double getMultiplier() {
            return 1.5;
        }
    },
    LEGENDARY{
        public double getMultiplier() {
            return 2;
        }
    };
    public double getMultiplier() {
        return 1;
    }
}
