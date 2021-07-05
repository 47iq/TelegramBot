package command.shop;

import data.CardDAO;
import game.*;
import communication.keyboard.KeyboardType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import communication.util.*;

@Component
public class OpenBoxCommand {

    private static final Logger LOGGER = LogManager.getLogger(OpenBoxCommand.class);

    @Autowired
    LootBox lootBox;
    @Autowired
    CardDAO cardDAO;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    ImageBase imageBase;

    public AnswerDTO execute(CommandDTO commandDTO, LootBoxType type) {
        try {
            Card card = lootBox.open(type, commandDTO.getUser());
            if (cardDAO.create(card))
                return new AnswerDTO(true,
                        MessageBundle.getMessage("info_youget") + "\n" + messageFormatter.getCardMessage(card),
                        KeyboardType.LEAF, imageBase.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
            else {
                LOGGER.error("Error while opening a lootbox");
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null);
        }
    }
}
