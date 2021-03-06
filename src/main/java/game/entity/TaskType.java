package game.entity;

public enum TaskType {
    CAVE {
        @Override
        public Long getValueNeeded(int level) {
            return level * 120L;
        }
    },
    PVP_BATTLE {
        @Override
        public Long getValueNeeded(int level) {
            return (long) level;
        }
    },
    BATTLE {
        @Override
        public Long getValueNeeded(int level) {
            return level * 30L;
        }
    },
    MONEY_EARNED {
        @Override
        public Long getValueNeeded(int level) {
            return level * 10000L;
        }
    };

    public Long getValueNeeded(int level) {
        return null;
    }
}
