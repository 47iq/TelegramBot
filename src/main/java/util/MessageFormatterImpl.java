package util;

import game.Card;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageFormatterImpl implements MessageFormatter{

    @Override
    public String getCardMessage(Card card) {
        return MessageBundle.getMessage(card.getType().name()) + " " + MessageBundle.getMessage(card.getName().name())
                + MessageBundle.getMessage("info_uid") + card.getUID() +") " + card.getLevel() +
                " " + MessageBundle.getMessage("info_level") + "\n" + MessageBundle.getMessage("info_health")
                + String.format("%.2f",card.getHealth()) + MessageBundle.getMessage("info_attack") + String.format("%.2f", card.getAttack()) +
                MessageBundle.getMessage("info_defence") + String.format("%.2f",card.getDefence());
    }
}
