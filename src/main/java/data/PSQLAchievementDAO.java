package data;

import game.entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.MessageBundle;

import java.util.Arrays;
import java.util.List;

public class PSQLAchievementDAO implements AchievementDAO{

    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLAchievementDAO.class);

    public PSQLAchievementDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg = new Configuration().configure(MessageBundle.getSetting("HIBERNATE_CONFIG")).
                        addResource("achievements.hbm.xml");
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
            LOGGER.info("Creating session for achievements");
        } catch (Exception e) {
            LOGGER.error("Failed to create sessionFactory object. " + Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Achievement> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<Achievement> achievements = session.createQuery("select p from " + Achievement.class.getSimpleName() + " p").list();
            System.out.println("select p from " + Achievement.class.getSimpleName() + " p");
            transaction.commit();
            System.out.println(achievements);
            return achievements;
        } catch (Exception e) {
            LOGGER.error("Error while parsing achievements table: " + e.getClass());
            return null;
        }
    }

    @Override
    public Achievement getEntityById(String UID) {
        return getAll().stream().filter(x ->  x.getOwner().equals(UID)).findAny().orElse(null);
    }

    @Override
    public Achievement update(Achievement achievement) {
        Transaction transaction = session.beginTransaction();
        session.update(achievement);
        transaction.commit();
        return achievement;
    }

    @Override
    public boolean delete(Achievement achievement) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(achievement);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean create(Achievement achievement) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(achievement);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding an achievement.");
            return false;
        }
    }
}
