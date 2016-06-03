package businessLogic;

import dataLayer.ExchangeRates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        //configuration.configure(Paths.get("D:\\Programy\\IntelliJIDEA\\Projekty\\NBPImporter\\src\\resources\\hibernate.cfg.xml").toFile());
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration
                .buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static int create(ExchangeRates exchangeRates, String table) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(table, exchangeRates);
        session.getTransaction().commit();
        session.close();
        return exchangeRates.getId();
    }

    public static ExchangeRates findByID(int id) {
        Session session = getSessionFactory().openSession();
        ExchangeRates exchangeRates = (ExchangeRates) session.load(ExchangeRates.class, id);
        session.close();
        return exchangeRates;
    }

    public static ArrayList<ExchangeRates> findByDate(String date) {
        Session session = getSessionFactory().openSession();
        List exchangeRates = session.createCriteria(ExchangeRates.class).add(Restrictions.like("date", date)).list();
        session.close();
        return (ArrayList) exchangeRates;
    }
}