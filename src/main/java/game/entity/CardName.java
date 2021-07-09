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
            return 0.90;
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
    },
    BILLIE_HARRINGTON {
        @Override
        public double gerMultiplier() {
            return 1.35;
        }
    },
    SVYATOSLAV {
        @Override
        public double gerMultiplier() {
            return 1.32;
        }
    },
    VOZIANOVA{
        @Override
        public double gerMultiplier() {
            return 1;
        }
    },
    PERTSEV {
        @Override
        public double gerMultiplier() {
            return 1.10;
        }
    };
    public static CardName valueOf(int val) {
        return switch (val) {
            case 0 -> KLIMENKOV;
            case 1 -> KOROBKOV;
            case 2 -> BALAKSHIN;
            case 3 -> GAVRILOV;
            case 4 -> POLYAKOV;
            case 5 -> PERTSEV;
            case 6 -> VOZIANOVA;
            default -> throw new IllegalStateException("Unexpected value: " + val);
        };
    }

    public static CardName specialValueOf(int rand) {
        return switch (rand) {
            case 0,1,2 -> BILLIE_HARRINGTON;
            case 3,4,5,6 -> SVYATOSLAV;
            default -> throw new IllegalStateException("Unexpected value: " + rand);
        };
    }

    public double gerMultiplier() {
        return  1;
    }
}
