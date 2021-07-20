package game.dungeon;

import communication.util.MessageBundle;
import game.entity.Card;

/**
 * Enemy type class
 */

public enum EnemyType {

    ANGRY_STUDENTS {
        @Override
        public Card getStats() {
            return new Card(null, 30, 1, 1);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("ANGRY_STUDENTS_enemy");
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
            return MessageBundle.getMessage("RECTOR_enemy");
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
            return MessageBundle.getMessage("EXPELLED_STUDENT_enemy");
        }

        @Override
        public long getAward() {
            return 1000;
        }
    },
    DUNGEON_MASTER {
        @Override
        public Card getStats() {
            return new Card(null, 10, 6, 1);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("DUNGEON_MASTER_enemy");
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
            return MessageBundle.getMessage("RAD_COCKROACH_enemy");
        }

        @Override
        public long getAward() {
            return 150;
        }
    },
    PROGRAMMING_LAB {
        @Override
        public Card getStats() {
            return new Card(null, 5, 2, 2);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("PROGRAMMING_LAB_enemy");
        }

        @Override
        public long getAward() {
            return 50;
        }
    },
    DEV {
        @Override
        public Card getStats() {
            return new Card(null, -232452, 234873855, 14841548);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("DEV_enemy");
        }

        @Override
        public long getAward() {
            return 100;
        }
    },
    WOLF {
        @Override
        public Card getStats() {
            return new Card(null, 5, 2, 3);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("WOLF_enemy");
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
            return MessageBundle.getMessage("STUDENT_OFFICE_enemy");
        }

        @Override
        public long getAward() {
            return 250;
        }
    },
    LARY {
        @Override
        public Card getStats() {
            return new Card(null, 4, 2, 3);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("LARY_enemy");
        }

        @Override
        public long getAward() {
            return 100;
        }
    },
    ANDREW {
        @Override
        public Card getStats() {
            return new Card(null, 7, 3, 4);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("ANDREW_enemy");
        }

        @Override
        public long getAward() {
            return 100;
        }
    },
    ROBOT {
        @Override
        public Card getStats() {
            return new Card(null, 8, 4, 15);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("ROBOT_enemy");
        }

        @Override
        public long getAward() {
            return 180;
        }
    },
    VIETNAM_GUY {
        @Override
        public Card getStats() {
            return new Card(null, 11, 5, 2);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("VIETNAM_GUY_enemy");
        }

        @Override
        public long getAward() {
            return 230;
        }
    },
    PLASTIC_WORLD {
        @Override
        public Card getStats() {
            return new Card(null, 14, 4, 12);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("PLASTIC_WORLD_enemy");
        }

        @Override
        public long getAward() {
            return 330;
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
}
