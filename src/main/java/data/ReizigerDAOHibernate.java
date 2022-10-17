package data;

import data.interfaces.AdresDAO;
import data.interfaces.ReizigerDAO;
import domain.Reiziger;
import org.hibernate.Session;
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
        session.persist(reiziger);
        session.flush();
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        session.merge(reiziger);
        session.flush();
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        session.remove(reiziger);
        session.flush();
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Query query = session.createQuery("from Reiziger where id = ?1");
        query.setParameter(1, id);

        Object reiziger = query.getSingleResult();

        if (reiziger instanceof Reiziger) {
            return (Reiziger) reiziger;
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        Date date = Date.valueOf(datum);

        Query query = session.createQuery("from Reiziger where geboortedatum = ?1");
        query.setParameter(1, date);

        List reizigers = query.list();

        if (!reizigers.isEmpty() && reizigers.get(0) instanceof Reiziger) {
            return (List<Reiziger>) reizigers;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Query query = session.createQuery("from Reiziger");

        List reizigers = query.list();

        if (!reizigers.isEmpty() && reizigers.get(0) instanceof Reiziger) {
            return (List<Reiziger>) reizigers;
        }
        return new ArrayList<>();
    }
}
