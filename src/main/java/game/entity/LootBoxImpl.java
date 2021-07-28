package game.entity;

import data.User;
import game.service.WeightedRandomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LootBoxImpl implements LootBox {

    @Autowired
    @Qualifier("card_random")
    WeightedRandomizer<CardName> lootBoxRandom;

    @Autowired
    @Qualifier("elite_card_random")
    WeightedRandomizer<CardName> eliteLootBoxRandom;

    @Autowired
    @Qualifier("task_card_random")
    WeightedRandomizer<CardName> taskBoxRandom;

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
            case TASK -> {
                return new Card(taskBoxCave(), CardType.LEGENDARY, user);
            }
        }
    }

    private CardName taskBoxCave() {
        return taskBoxRandom.getRandom();
    }

    private CardName superBoxRandName() {
        return eliteLootBoxRandom.getRandom();
    }

    private CardName randName() {
        return lootBoxRandom.getRandom();
    }
}
