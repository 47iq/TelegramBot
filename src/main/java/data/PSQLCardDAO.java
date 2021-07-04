package data;

import game.Card;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PSQLCardDAO implements CardDAO{

    private List<Card> cardCache = new ArrayList<>();
    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLCardDAO.class);


    public PSQLCardDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg = new Configuration().
                        addResource("card.hbm.xml").configure();
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
            LOGGER.info("Создание сессии для таблицы cards");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Card> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<Card> users = session.createQuery("select p from " + Card.class.getSimpleName() + " p").list();
            transaction.commit();
            cardCache = users;
            return cardCache;
        } catch (Exception e) {
            //todo
            System.err.println(e.toString() +  " while getting users.");
            LOGGER.error(e.toString() +  " while getting users.");
            return null;
        }
    }

    @Override
    public Card getEntityById(long UID) {
        return cardCache.stream().filter(x ->  x.getUID().equals(UID)).findAny().orElse(null);
    }

    @Override
    public Card update(Card card) {
        //todo
        return null;
    }

    @Override
    public boolean delete(long UID) {
        //todo
        return false;
    }

    @Override
    public boolean create(Card card) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(card);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding a card.");
            return false;
        }
    }
}
