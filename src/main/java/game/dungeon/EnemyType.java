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
            return new Card(null, 10, 6, 1);
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
    PROGRAMMING_LAB {
        @Override
        public Card getStats() {
            return new Card(null, 5, 2, 2);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_proglab");
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
            return MessageBundle.getMessage("enemy_dev");
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
            return MessageBundle.getMessage("enemy_wolf");
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
            return 250;
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
        //todo
        System.out.println(rnd);
        if (rnd < 20)
            return ANGRY_STUDENTS;
        else if(rnd < 35)
            return PROGRAMMING_LAB;
        else if(rnd < 50)
            return WOLF;
        else if (rnd < 60)
            return RAD_COCKROACH;
        else if (rnd < 70)
            return DUNGEON_MASTER;
        else if (rnd < 85)
            return STUDENT_OFFICE;
        else if (rnd < 90)
            return EXPELLED_STUDENT;
        else if(rnd < 98)
            return DEV;
        else
            return RECTOR;
    }
}
