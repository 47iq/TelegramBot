package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PSQLUserDAO implements UserDAO{

    private List<User> usersCache;
    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLUserDAO.class);

    public PSQLUserDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg = new Configuration().
                        addResource("user.hbm.xml").configure();
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
            LOGGER.info("Создание сессии для таблицы users");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("select p from "+ User.class.getSimpleName() + " p").list();
            transaction.commit();
            usersCache = users;
            //todo
            System.out.println(usersCache);
            return usersCache;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("ERROR while getting users: " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public User getEntityById(String UID) {
        System.out.println(usersCache);
        //todo
        return usersCache.stream().filter(x -> x.getUID().equals(UID)).findAny().orElse(null);
    }

    @Override
    public User update(User user) {
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        usersCache = usersCache.stream().filter(x -> !x.getUID().equals(user.getUID())).collect(Collectors.toList());
        usersCache.add(user);
        return user;
    }

    @Override
    public boolean delete(String UID) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(UID);
            transaction.commit();
            usersCache = usersCache.stream().filter(x -> !x.getUID().equals(UID)).collect(Collectors.toList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean create(User user) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(user);
            usersCache.add(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding a user.");
            return false;
        }
    }
}
