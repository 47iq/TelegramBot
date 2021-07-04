package game;

public enum CardType {
    BASIC,
    RARE{
        double getMultiplier() {
            return 1.25;
        }
    },
    EPIC{
        double getMultiplier() {
            return 1.5;
        }
    },
    LEGENDARY{
        double getMultiplier() {
            return 2;
        }
    };
    double getMultiplier() {
        return 1;
    }
}
