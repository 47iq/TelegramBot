package game.entity;

import data.User;

/**
 * Interface of a loot box
 */

public interface LootBox {

    /**
     * Method that opens a loot box of a certain type for a user
     * @param type type of loot box
     * @param user user
     * @return card which is in a loot box
     */

    Card open(LootBoxType type, User user);
}
