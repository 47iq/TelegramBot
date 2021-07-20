package command.service_command;

import data.CardDAO;
import communication.keyboard.KeyboardType;
import data.CardService;
import data.User;
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
import util.MessageBundle;
import util.MessageFormatter;

/**
 * Command, which makes user open a lootbox of a certain type.
 * Service command, used in other commands and code only.
 */

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
    @Autowired
    CardService cardService;

    public AnswerDTO execute(CommandDTO commandDTO, LootBoxType type) {
        try {
            Card card = lootBox.open(type, commandDTO.getUser());
            User user = commandDTO.getUser();
            if(!type.equals(LootBoxType.SUPER_RARE) && cardService.getAllCardsOf(user).size() > Long.parseLong(MessageBundle.getSetting("MAX_CARDS")))
                return new AnswerDTO(true, MessageBundle.getMessage("err_maxcards"), KeyboardType.SHOP, null, null, user);
            if (cardDAO.create(card)) {
                LOGGER.info(commandDTO.getUser().getUID() + " gets: " + card.getType() + " "+card.getName() + ": "
                        + card.getMaxHealth() + ", " + card.getAttack() + ", " + card.getDefence());
                AnswerDTO answerDTO = new AnswerDTO(true,
                        MessageBundle.getMessage("info_youget") + "\n" + messageFormatter.getCardMessage(card),
                        KeyboardType.LEAF, imageParser.getImage(new ImageIdentifier(card.getName(), card.getType())), null, commandDTO.getUser());
                answerDTO.setCardName(card.getName());
                return answerDTO;
            } else {
                LOGGER.error("Error while opening a lootbox");
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null, commandDTO.getUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AnswerDTO(false, MessageBundle.getMessage("err_unk"), KeyboardType.CLASSIC, null, null, commandDTO.getUser());
        }
    }
}
