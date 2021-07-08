package game.entity;

import data.User;

public class LootBoxImpl implements LootBox {

    @Override
    public Card open(LootBoxType type, User user) {
        double rnd = Math.random() * 100;
        switch (type) {
            default -> {
                if (rnd < 75)
                    return new Card(randName(), CardType.BASIC, user);
                else if (rnd < 90)
                    return new Card(randName(), CardType.RARE, user);
                else if (rnd < 99.5)
                    return new Card(randName(), CardType.EPIC, user);
                else
                    return new Card(randName(), CardType.LEGENDARY, user);
            }
            case ADVANCED -> {
                if (rnd < 55)
                    return new Card(randName(), CardType.BASIC, user);
                else if (rnd < 80)
                    return new Card(randName(), CardType.RARE, user);
                else if (rnd < 96)
                    return new Card(randName(), CardType.EPIC, user);
                else
                    return new Card(randName(), CardType.LEGENDARY, user);
            }
            case PRO -> {
                if (rnd < 40)
                    return new Card(randName(), CardType.BASIC, user);
                else if (rnd < 70)
                    return new Card(randName(), CardType.RARE, user);
                else if (rnd < 90)
                    return new Card(randName(), CardType.EPIC, user);
                else
                    return new Card(randName(), CardType.LEGENDARY, user);
            }
            case SUPER_RARE -> {
                return new Card(superBoxRandName(), CardType.LEGENDARY, user);
            }
        }
    }

    private CardName superBoxRandName() {
        int rand = (int) (Math.random() * 7);
        return CardName.specialValueOf(rand);
    }

    private CardName randName() {
        int rand = (int) (Math.random() * 7);
        return CardName.valueOf(rand);
    }
}
