import game.dungeon.EnemyType;
import org.apache.http.util.Asserts;
import org.junit.Before;
import org.junit.Test;
import util.MessageBundle;

import java.util.ResourceBundle;
import java.util.Set;

public class MessageTest extends Asserts {

    @Test
    public void execute() {
        Set<String> messages = ResourceBundle.getBundle("messages").keySet();
        Set<String> itmoMessages = ResourceBundle.getBundle("messages_itmo").keySet();
        Set<String> sch9Messages = ResourceBundle.getBundle("messages_sch9").keySet();
        messages.stream().forEach(x -> {
            if(ResourceBundle.getBundle("messages_itmo").containsKey(x))
                System.err.println("Duplicate key in messages and messages_itmo " + x);
            if(ResourceBundle.getBundle("messages_sch9").containsKey(x))
                System.err.println("Duplicate key in messages and messages_sch9 " + x);
        });
        sch9Messages.stream().forEach(x -> {
            if(ResourceBundle.getBundle("messages_itmo").getString(x).equals(ResourceBundle.getBundle("messages_sch9").getString(x)))
                System.err.println("Duplicate key in messages_itmo and messages_sch9 " + x);
        });
    }
}
