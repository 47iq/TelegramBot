package game;

import data.User;

public interface LootBox {
    Card open(LootBoxType type, User user);
}
