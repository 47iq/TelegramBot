package data;

import game.quest.QuestState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.MessageBundle;

import java.util.Arrays;
import java.util.List;

public class PQSLQuestDAO implements QuestDAO{
    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLCardDAO.class);

    public PQSLQuestDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg = new Configuration().configure(MessageBundle.getSetting("HIBERNATE_CONFIG")).
                        addResource("quest.hbm.xml");
                serviceRegistry = new StandardServiceRegistryBuilder().
                        applySettings(cfg.getProperties()).build();
                sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            } catch (Throwable e) {
                LOGGER.error("Failed to create sessionFactory object. " + Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
            session = sessionFactory.openSession();
            getAll();
            LOGGER.info("Creating session for quests table");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<QuestState> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<QuestState> questStates = session.createQuery("select p from " + QuestState.class.getSimpleName() + " p").list();
            transaction.commit();
            return questStates;
        } catch (Exception e) {
            LOGGER.error("Error while parsing tasks table: " + e.getClass());
            return null;
        }
    }

    @Override
    public QuestState getEntityById(long UID) {
        return getAll().stream().filter(x ->  x.getUID() == (UID)).findAny().orElse(null);
    }

    @Override
    public QuestState update(QuestState questState) {
        Transaction transaction = session.beginTransaction();
        session.update(questState);
        transaction.commit();
        return questState;
    }

    @Override
    public boolean delete(QuestState questState) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(questState);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean create(QuestState questState) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(questState);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding a card.");
            return false;
        }
    }
}
