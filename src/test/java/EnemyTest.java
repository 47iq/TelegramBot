import game.dungeon.EnemyType;
import game.entity.CardName;
import game.entity.CardType;
import game.entity.ImageIdentifier;
import game.service.ImageParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EnemyTest extends Assert {

    @Before
    public void init() {
    }

    @Test
    public void execute() {
        System.err.println("\nRunning defined dungeon enemy tests:");
        for (EnemyType val : EnemyType.values()) {
            System.out.print(val);
            String message = ResourceBundle.getBundle("messages").getString(val.name());
            assertNotNull(message);
            assertNotEquals(message,"");
            message = ResourceBundle.getBundle("messages").getString(val.name() + "_name");
            assertNotNull(message);
            assertNotEquals(message,"");
            message = ResourceBundle.getBundle("messages").getString(val.name() + "_winhook");
            assertNotNull(message);
            assertNotEquals(message,"");
            message = ResourceBundle.getBundle("messages").getString(val.name() + "_losehook");
            assertNotNull(message);
            assertNotEquals(message,"");
            message = ResourceBundle.getBundle("messages").getString(val.name() + "_enemy");
            assertNotNull(message);
            assertNotEquals(message,"");
            System.out.println(" has been passed");
        }
    }
}
