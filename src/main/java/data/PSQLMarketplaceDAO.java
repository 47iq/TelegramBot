package data;

import game.marketplace.Merchandise;
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

public class PSQLMarketplaceDAO implements MarketplaceDAO{

    private Session session = null;

    private static final Logger LOGGER = LogManager.getLogger(PSQLMarketplaceDAO.class);

    public PSQLMarketplaceDAO() {
        connect();
    }

    private void connect() {
        SessionFactory sessionFactory;
        ServiceRegistry serviceRegistry;
        try {
            try {
                Configuration cfg =  new Configuration().configure(MessageBundle.getSetting("HIBERNATE_CONFIG")).
                        addResource("marketplace.hbm.xml");
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
            LOGGER.info("Creating session for marketplace table");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Merchandise> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            List<Merchandise> merchandises = session.createQuery("select p from "+ Merchandise.class.getSimpleName() + " p").list();
            transaction.commit();
            return merchandises;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("ERROR while getting merchandises: " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public Merchandise getEntityById(long UID) {
        return getAll().stream().filter(x -> x.getCardUID() == UID).findAny().orElse(null);
    }

    @Override
    public Merchandise update(Merchandise merchandise) {
        Transaction transaction = session.beginTransaction();
        session.update(merchandise);
        transaction.commit();
        return merchandise;
    }

    @Override
    public boolean delete(long UID) {
        try {
            Transaction transaction = session.beginTransaction();
            Merchandise merchandise = session.load(Merchandise.class, UID);
            session.delete(merchandise);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean create(Merchandise merchandise) {
        try {
            Transaction tx = session.beginTransaction();
            session.save(merchandise);
            tx.commit();
            return true;
        } catch (Exception e) {
            LOGGER.warn(Arrays.toString(e.getStackTrace()) +  " while adding a merchandise.");
            return false;
        }
    }
}
