package data;

import game.entity.Card;
import game.entity.Task;
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

public class PSQLTaskDAO implements TaskDAO{

    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLCardDAO.class);

    public PSQLTaskDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg = new Configuration().configure(MessageBundle.getSetting("HIBERNATE_CONFIG")).
                        addResource("task.hbm.xml");
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
            LOGGER.info("Creating sesson for tasks table");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Task> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<Task> tasks = session.createQuery("select p from " + Task.class.getSimpleName() + " p").list();
            transaction.commit();
            return tasks;
        } catch (Exception e) {
            LOGGER.error("Error while parsing tasks table: " + e.getClass());
            return null;
        }
    }

    @Override
    public Task getEntityById(long UID) {
        return getAll().stream().filter(x ->  x.getUid() == (UID)).findAny().orElse(null);
    }

    @Override
    public Task update(Task task) {
        Transaction transaction = session.beginTransaction();
        session.update(task);
        transaction.commit();
        return task;
    }

    @Override
    public boolean delete(Task task) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean create(Task task) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(task);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding a card.");
            return false;
        }
    }
}
