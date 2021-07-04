package game;

public enum CardName {
    KLIMENKOV {
        @Override
        double gerMultiplier() {
            return 1.25;
        }
    },
    KOROBKOV{
        @Override
        double gerMultiplier() {
            return 0.75;
        }
    },
    BALAKSHIN{
        @Override
        double gerMultiplier() {
            return 1.05;
        }
    },
    GAVRILOV{
        @Override
        double gerMultiplier() {
            return 1.1;
        }
    },
    POLYAKOV{
        @Override
        double gerMultiplier() {
            return 1;
        }
    };
    static CardName valueOf(int val) {
        return switch (val) {
            case 0 -> KLIMENKOV;
            case 1 -> KOROBKOV;
            case 2 -> BALAKSHIN;
            case 3 -> GAVRILOV;
            case 4 -> POLYAKOV;
            default -> throw new IllegalStateException("Unexpected value: " + val);
        };
    }

    double gerMultiplier() {
        return  1;
    }
}
