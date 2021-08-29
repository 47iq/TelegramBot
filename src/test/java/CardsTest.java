import game.entity.CardName;
import game.entity.CardType;
import game.entity.ImageIdentifier;
import game.service.ImageParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.MessageBundle;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CardsTest extends Assert {
    ImageParser imageParser;
    Map<CardName, CardType> exclusions;
    Set<CardName> taskCards;

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        imageParser = context.getBean(ImageParser.class);
        exclusions = new HashMap<>();
        exclusions.put(CardName.STANKEVICH, CardType.LEGENDARY);
        exclusions.put(CardName.BILLIE_HARRINGTON, CardType.LEGENDARY);
        exclusions.put(CardName.SVYATOSLAV, CardType.LEGENDARY);
        exclusions.put(CardName.TASK_1, CardType.LEGENDARY);
        exclusions.put(CardName.TASK_2, CardType.LEGENDARY);
        taskCards = new HashSet<>();
        taskCards.add(CardName.TASK_1);
        taskCards.add(CardName.TASK_2);
    }

    @Test
    public void execute() {
        System.err.println("Running defined cards tests:");
        for (CardName val : CardName.values())
            for (CardType rar : CardType.values()) {
                System.out.print(val + " " + rar);
                if(exclusions.containsKey(val)) {
                    File image = imageParser.getImage(new ImageIdentifier(val, exclusions.get(val)));
                    if(!taskCards.contains(val)) {
                        String message = MessageBundle.getMessage(val.name() + "_dropmsg");
                        assertNotNull(message);
                    }
                    assertNotNull(image);
                } else {
                    File image = imageParser.getImage(new ImageIdentifier(val, rar));
                    assertNotNull(image);
                }
                String shortMsg = MessageBundle.getMessage(val.name() + "_short");
                assertNotNull(shortMsg);
                System.out.println(" has been passed");
            }
    }
}
