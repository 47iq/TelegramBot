package data;

import game.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.MessageBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PSQLCardDAO implements CardDAO{

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
                Configuration cfg = new Configuration().configure(MessageBundle.getSetting("HIBERNATE_CONFIG")).
                        addResource("card.hbm.xml");
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
            return users;
        } catch (Exception e) {
            LOGGER.error("Error while parsing cards table: " + e.getClass());
            return null;
        }
    }

    @Override
    public Card getEntityById(long UID) {
        return getAll().stream().filter(x ->  x.getUID().equals(UID)).findAny().orElse(null);
    }

    @Override
    public Card update(Card card) {
        Transaction transaction = session.beginTransaction();
        session.update(card);
        transaction.commit();
        return card;
    }

    @Override
    public boolean delete(Card card) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(card);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
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
