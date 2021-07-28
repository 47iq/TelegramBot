package game.entity;

public enum RewardType {
    LOOT_BOX {
        @Override
        public long getAmount(long level) {
            return 1;
        }
    },
    MONEY {
        @Override
        public long getAmount(long level) {
            return level * 1000;
        }
    },
    HEAL {
        @Override
        public long getAmount(long level) {
            return level;
        }
    };

    public long getAmount(long level) {
        return 0;
    }
}
