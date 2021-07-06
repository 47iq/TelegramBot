package game.dungeon;

import communication.util.MessageBundle;
import game.entity.Card;

public enum EnemyType {

    ANGRY_STUDENTS {
        @Override
        public Card getStats() {
            return new Card(null, 30, 1, 1);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_students");
        }

        @Override
        public long getAward() {
            return 10;
        }
    },
    RECTOR {
        @Override
        public Card getStats() {
            return new Card(null, 10000, 1000, 1000);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_rector");
        }

        @Override
        public long getAward() {
            return 1000000;
        }
    },
    EXPELLED_STUDENT {
        @Override
        public Card getStats() {
            return new Card(null, 30, 15, 10);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_expelled");
        }

        @Override
        public long getAward() {
            return 1000;
        }
    },
    DUNGEON_MASTER {
        @Override
        public Card getStats() {
            return new Card(null, 17, 12, 1);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_dungeon_master");
        }

        @Override
        public long getAward() {
            return 300;
        }
    },
    RAD_COCKROACH {
        @Override
        public Card getStats() {
            return new Card(null, 10, 2, 2);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_rad_cockroach");
        }

        @Override
        public long getAward() {
            return 150;
        }
    },
    STUDENT_OFFICE {
        @Override
        public Card getStats() {
            return new Card(null, 12, 4, 4);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_so");
        }

        @Override
        public long getAward() {
            return 200;
        }
    };

    public Card getStats() {
        return new Card(null, 10, 5, 5);
    }

    public String getBattleMessage() {
        return MessageBundle.getMessage("enemy_default");
    }

    public long getAward() {
        return 200;
    }

    public static EnemyType getRandom() {
        int rnd = (int) (Math.random() * 100);
        if (rnd < 20)
            return ANGRY_STUDENTS;
        else if (rnd < 40)
            return RAD_COCKROACH;
        else if (rnd < 60)
            return STUDENT_OFFICE;
        else if (rnd < 75)
            return EXPELLED_STUDENT;
        else if (rnd < 95)
            return DUNGEON_MASTER;
        else
            return RECTOR;
    }
}
