import game.entity.CardName;
import game.entity.CardType;
import game.entity.ImageIdentifier;
import game.service.BattleService;
import game.service.ImageParser;
import game.service.ImageParserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CardsTest extends Assert {
    ImageParser imageParser;
    Map<CardName, CardType> exclusions;

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        imageParser = context.getBean(ImageParser.class);
        exclusions = new HashMap<>();
        exclusions.put(CardName.STANKEVICH, CardType.LEGENDARY);
        exclusions.put(CardName.BILLIE_HARRINGTON, CardType.LEGENDARY);
        exclusions.put(CardName.SVYATOSLAV, CardType.LEGENDARY);
    }

    @Test
    public void execute() {
        System.err.println("Running defined cards tests:");
        for (CardName val : CardName.values())
            for (CardType rar : CardType.values()) {
                System.out.print(val + " " + rar);
                if(exclusions.containsKey(val)) {
                    File image = imageParser.getImage(new ImageIdentifier(val, exclusions.get(val)));
                    String message = ResourceBundle.getBundle("messages").getString(val.name() + "_dropmsg");
                    assertNotNull(message);
                    assertNotNull(image);
                } else {
                    File image = imageParser.getImage(new ImageIdentifier(val, rar));
                    assertNotNull(image);
                }
                String shortMsg = ResourceBundle.getBundle("messages").getString(val.name() + "_short");
                assertNotNull(shortMsg);
                System.out.println(" has been passed");
            }
    }
}