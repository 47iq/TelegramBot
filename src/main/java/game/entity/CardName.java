package game.entity;

/**
 * Class that represents card name
 */

public enum CardName {
    KLIMENKOV {
        @Override
        public double gerMultiplier() {
            return 1.25;
        }
    },
    KOROBKOV {
        @Override
        public double gerMultiplier() {
            return 0.90;
        }
    },
    BALAKSHIN {
        @Override
        public double gerMultiplier() {
            return 1.05;
        }
    },
    GAVRILOV {
        @Override
        public double gerMultiplier() {
            return 1.1;
        }
    },
    POLYAKOV {
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
    VOZIANOVA {
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
    },
    STANKEVICH {
        @Override
        public double gerMultiplier() {
            return 1.34;
        }
    },
    TASK_1 {
        @Override
        public double gerMultiplier() {
            return 1.50;
        }
    },
    TASK_2 {
        @Override
        public double gerMultiplier() {
            return 1.50;
        }
    };

    /**
     * Method that gets card name for a basic, advanced and pro loot boxes from a number
     *
     * @param val type of card
     * @return card name
     */

    @Deprecated
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

    public double gerMultiplier() {
        return 1;
    }
}
