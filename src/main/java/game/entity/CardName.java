package game.entity;

public enum CardName {
    //DUNGEON_ENEMY,
    KLIMENKOV {
        @Override
        public double gerMultiplier() {
            return 1.25;
        }
    },
    KOROBKOV{
        @Override
        public double gerMultiplier() {
            return 0.75;
        }
    },
    BALAKSHIN{
        @Override
        public double gerMultiplier() {
            return 1.05;
        }
    },
    GAVRILOV{
        @Override
        public double gerMultiplier() {
            return 1.1;
        }
    },
    POLYAKOV{
        @Override
        public double gerMultiplier() {
            return 1;
        }
    };
    public static CardName valueOf(int val) {
        return switch (val) {
            case 0 -> KLIMENKOV;
            case 1 -> KOROBKOV;
            case 2 -> BALAKSHIN;
            case 3 -> GAVRILOV;
            case 4 -> POLYAKOV;
            default -> throw new IllegalStateException("Unexpected value: " + val);
        };
    }

    public double gerMultiplier() {
        return  1;
    }
}
