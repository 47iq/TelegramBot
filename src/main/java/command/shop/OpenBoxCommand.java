package command.shop;

import data.CardDAO;
import communication.keyboard.KeyboardType;
import game.entity.Card;
import game.entity.ImageIdentifier;
import game.entity.LootBox;
import game.entity.LootBoxType;
import game.service.ImageParser;
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
    ImageParser imageParser;

    public AnswerDTO execute(CommandDTO commandDTO, LootBoxType type) {
        try {
            Card card = lootBox.open(type, commandDTO.getUser());
            if (cardDAO.create(card)) {
                AnswerDTO answerDTO = new AnswerDTO(true,
                        MessageBundle.getMessage("info_youget") + "\n" + messageFormatter.getCardMessage(card),
                        KeyboardType.LEAF, imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null);
                answerDTO.setCardName(card.getName());
                return answerDTO;
            } else {
                LOGGER.error("Error while opening a lootbox");
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null);
        }
    }
}
