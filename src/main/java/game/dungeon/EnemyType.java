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
    },
    LARY {
        @Override
        public Card getStats() {
            return new Card(null, 4, 2, 3);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_lary");
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
            return MessageBundle.getMessage("enemy_andrew");
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
            return MessageBundle.getMessage("enemy_robot");
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
            return MessageBundle.getMessage("enemy_vietnam");
        }

        @Override
        public long getAward() {
            return 230;
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
        double rnd = (Math.random() * 100);
        //todo
        System.out.println("Enemy: " + rnd);
        if (rnd < 8)
            return ANGRY_STUDENTS;
        else if(rnd < 16)
            return ROBOT;
        else if(rnd <  24)
            return LARY;
        else if(rnd < 32)
            return PROGRAMMING_LAB;
        else if(rnd < 40)
            return ANDREW;
        else if(rnd < 48)
            return WOLF;
        else if (rnd < 56)
            return RAD_COCKROACH;
        else if(rnd < 64)
            return VIETNAM_GUY;
        else if (rnd < 72)
            return DUNGEON_MASTER;
        else if (rnd < 80)
            return STUDENT_OFFICE;
        else if (rnd < 88)
            return EXPELLED_STUDENT;
        else if(rnd < 98)
            return DEV;
        else
            return RECTOR;
    }
}
