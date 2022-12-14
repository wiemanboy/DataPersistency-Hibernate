package data;

import data.interfaces.AdresDAO;
import data.interfaces.ReizigerDAO;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.persist(reiziger);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.merge(reiziger);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.remove(reiziger);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Reiziger where id = ?1", Reiziger.class);
        query.setParameter(1, id);

        Object reiziger = query.getSingleResult();

        transaction.commit();
        if (reiziger instanceof Reiziger) {
            return (Reiziger) reiziger;
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Date date = Date.valueOf(datum);

        Query query = session.createQuery("from Reiziger where geboortedatum = ?1", Reiziger.class);
        query.setParameter(1, date);

        List reizigers = query.list();

        transaction.commit();
        if (!reizigers.isEmpty() && reizigers.get(0) instanceof Reiziger) {
            return (List<Reiziger>) reizigers;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Reiziger", Reiziger.class);

        List reizigers = query.list();

        transaction.commit();
        if (!reizigers.isEmpty() && reizigers.get(0) instanceof Reiziger) {
            return (List<Reiziger>) reizigers;
        }
        return new ArrayList<>();
    }
}
