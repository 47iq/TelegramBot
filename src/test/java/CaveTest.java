import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import data.CardDAO;
import game.service.CardService;
import game.entity.User;
import game.service.UserService;
import game.dungeon.CaveService;
import game.entity.Card;
import game.entity.CardName;
import game.entity.CardType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ResourceBundle;

public class CaveTest extends Assert {

    CaveService caveService;
    UserService userService;
    CardService cardService;
    CardDAO cardDAO;
    CommandDTO commandDTOfirst;
    CommandDTO commandDTO;
    Card card;
    User user;
    long money;

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        caveService = context.getBean(CaveService.class);
        userService = context.getBean(UserService.class);
        cardService = context.getBean(CardService.class);
        cardDAO = context.getBean(CardDAO.class);
        user = userService.getUserData(new User(ResourceBundle.getBundle("test_settings").getString("username"), 0));
        card = new Card(CardName.BILLIE_HARRINGTON, CardType.LEGENDARY, user);
        card.setAttack(100000000);
        card.setDefence(100000000);
        card.setMaxHealth(100000000);
        card.setHealth(100000000);
        assertTrue(cardDAO.create(card));
        commandDTOfirst = new CommandDTO(user, "/dungeon_enter");
        commandDTO = new CommandDTO(user, "/dungeon_next");
        money = user.getTokens();
        userService.addTokens(user, 100000);
    }

    @Test
    public void execute() {
        System.err.println("Running random cave tests:");
        caveService.enterCaves(commandDTOfirst, card);
        for(int i = 0; i < 1000; i++) {
            AnswerDTO answerDTO= caveService.enterNextCave(commandDTO);
            System.out.println(answerDTO);
            if(answerDTO.getKeyboard() == KeyboardType.DUNGEON_LEAF)
                return;
            System.out.println(" has been completed");
        }

    }

    @After
    public void hook() {
        long balance = userService.getBalance(user);
        userService.spendTokens(user, balance - money);
        cardDAO.delete(card);
    }
}
