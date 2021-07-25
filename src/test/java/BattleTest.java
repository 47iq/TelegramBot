import game.entity.Card;
import game.entity.CardName;
import game.service.BattleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.Set;

public class BattleTest extends Assert {
    BattleService battleService;
    Set<TestData> tests;

    class TestData {
        Card first;
        Card second;

        public TestData(Card first, Card second) {
            this.first = first;
            this.second = second;
        }
    }

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        battleService = context.getBean(BattleService.class);
        tests = new HashSet<>();
        tests.add(new TestData(new Card(CardName.STANKEVICH, -11, 2, 3), new Card(null, 1111, 2, 3)));
        tests.add(new TestData(new Card(CardName.GAVRILOV, 111, 2, 300000), new Card(CardName.BALAKSHIN, 111, 222222, 3)));
        tests.add(new TestData(new Card(CardName.KLIMENKOV, 111, 2, 3), new Card(CardName.BILLIE_HARRINGTON, 222, 1, 55)));
    }

    @Test
    public void execute() {
        System.err.println("Running defined battle tests:");
        for(var test: tests) {
            System.out.print(test.first + " " + test.second);
            battleService.completeFastBattle(new StringBuilder(), test.first, test.second);
            System.out.println(" has been completed");
        }
        runRandomTests();
    }

    private void runRandomTests() {
        System.out.println("\n\n");
        System.err.println("Running random battle tests:");
        for(int i = 0; i < 1000; i++) {
            int type1 = (int) (Math.random() * 7);
            int type2 = (int) (Math.random() * 7);
            int attack1 = (int) (Math.random() * 10000);
            int attack2 = (int) (Math.random() * 10000);
            int defence1 = (int) (Math.random() * 10000);
            int defence2 = (int) (Math.random() * 10000);
            int hp1 = (int) (Math.random() * 10000);
            int hp2 = (int) (Math.random() * 10000);
            Card first = new Card(CardName.valueOf(type1), hp1, attack1, defence1);
            Card second = new Card(CardName.valueOf(type2), hp2, attack2, defence2);
            System.out.print(first + " " + second);
            battleService.completeFastBattle(new StringBuilder(), first, second);
            System.out.println(" has been completed");
        }
    }
}
