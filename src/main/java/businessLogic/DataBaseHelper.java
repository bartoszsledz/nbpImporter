package businessLogic;

import dataLayer.ExchangeRates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.util.List;

public class DataBaseHelper {

    private static SessionFactory getSessionFactory() {
        final Configuration configuration = new Configuration();
        final String hibernatePropsFilePath = "src/resources/hibernate.cfg.xml";
        final File hibernatePropsFile = new File(hibernatePropsFilePath);
        configuration.configure(hibernatePropsFile);

        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    static void create(final List<ExchangeRates> exchangeRates) {
        final Session session = getSessionFactory().openSession();
        session.beginTransaction();
        exchangeRates.forEach(session::save);
        session.getTransaction().commit();
        session.close();
    }

    static List<ExchangeRates> getData() {
        final Session session = getSessionFactory().openSession();
        final List exchangeRates = session.createCriteria(ExchangeRates.class).list();
        session.close();
        return exchangeRates;
    }

    public static ExchangeRates findByID(final int id) {
        final Session session = getSessionFactory().openSession();
        final ExchangeRates exchangeRates = (ExchangeRates) session.load(ExchangeRates.class, id);
        session.close();
        return exchangeRates;
    }

    static List<ExchangeRates> findByDate(final String date) {
        final Session session = getSessionFactory().openSession();
        final List exchangeRates = session.createCriteria(ExchangeRates.class).add(Restrictions.like("date", date)).list();
        session.close();
        return exchangeRates;
    }
}