package game.battle;

public enum AttackType {
    HEAD {
        @Override
        public double getBaseChance() {
            return 0.25;
        }

        @Override
        public double getMultiplier() {
            return 2;
        }

        @Override
        public DefenceType getContre() {
            return DefenceType.HEAD;
        }
    },
    BODY {
        @Override
        public DefenceType getContre() {
            return DefenceType.BODY;
        }
    },
    LEGS {
        @Override
        public double getBaseChance() {
            return 0.75;
        }

        @Override
        public double getMultiplier() {
            return 0.75;
        }

        @Override
        public DefenceType getContre() {
            return DefenceType.LEGS;
        }
    },
    HEAL,
    SELF_KILL;

    public double getBaseChance() {
        return 0.50;
    }

    public double getMultiplier() {
        return 1;
    }

    public  DefenceType getContre() {
        return null;
    }
}
