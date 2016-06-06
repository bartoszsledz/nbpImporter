package businessLogic;

import dataLayer.ExchangeRates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        String hibernatePropsFilePath = "src/resources/hibernate.cfg.xml";
        File hibernatePropsFile = new File(hibernatePropsFilePath);
        configuration.configure(hibernatePropsFile);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration
                .buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static void create(List<ExchangeRates> exchangeRates) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        exchangeRates.forEach(session::save);
        session.getTransaction().commit();
        session.close();
    }

    public static List getData() {
        Session session = getSessionFactory().openSession();
        List exchangeRates = session.createCriteria(ExchangeRates.class).list();
        session.close();
        return exchangeRates;
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