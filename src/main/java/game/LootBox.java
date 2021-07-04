package game;

import model.User;

public interface LootBox {
    Card open(LootBoxType type, User user);
}
