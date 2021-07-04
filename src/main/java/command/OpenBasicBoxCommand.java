package command;

import data.CardDAO;
import data.PSQLCardDAO;
import game.Card;
import game.ImageBase;
import game.LootBox;
import game.LootBoxType;
import keyboard.KeyboardType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.*;

import java.util.Arrays;

@Component
public class OpenBasicBoxCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(OpenBasicBoxCommand.class);

    @Autowired
    LootBox lootBox;
    @Autowired
    CardDAO cardDAO;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    ImageBase imageBase;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        try {
            Card card = lootBox.open(LootBoxType.BASIC, commandDTO.getUser());
            if (cardDAO.create(card))
                return new AnswerDTO(true,
                        MessageBundle.getMessage("info_youget") + "\n" + messageFormatter.getCardMessage(card),
                        KeyboardType.MENU, imageBase.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
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
