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
    },
    PLASTIC_WORLD {
        @Override
        public Card getStats() {
            return new Card(null, 14, 4, 12);
        }

        @Override
        public String getBattleMessage() {
            return MessageBundle.getMessage("enemy_plastic");
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

    /**
     * Random Enemy type generator
     * @return random enemy type
     */

    public static EnemyType getRandom() {
        double rnd = (Math.random() * 100);
        //todo
        System.out.println("Enemy: " + rnd);
        if (rnd < 7)
            return ANGRY_STUDENTS;
        else if(rnd < 15)
            return ROBOT;
        else if(rnd <  22)
            return LARY;
        else if(rnd < 30)
            return PROGRAMMING_LAB;
        else if(rnd < 37)
            return ANDREW;
        else if(rnd < 45)
            return WOLF;
        else if (rnd < 52)
            return RAD_COCKROACH;
        else if(rnd < 60)
            return VIETNAM_GUY;
        else if (rnd < 67)
            return DUNGEON_MASTER;
        else if (rnd < 75)
            return STUDENT_OFFICE;
        else if (rnd < 82)
            return EXPELLED_STUDENT;
        else if (rnd < 90)
            return PLASTIC_WORLD;
        else if(rnd < 99)
            return DEV;
        else
            return RECTOR;
    }
}
