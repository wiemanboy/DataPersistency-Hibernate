package data;


import data.interfaces.AdresDAO;
import domain.Adres;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        session.persist(adres);
        session.flush();
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        session.merge(adres);
        session.flush();
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        session.remove(adres);
        session.flush();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Query query = session.createQuery("from Adres where id = ?1");
        query.setParameter(1, id);

        Object adresList = query.getSingleResult();

        if (adresList instanceof Adres) {
            return (Adres) adresList;
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Query query = session.createQuery("from Adres where reiziger = ?1");
        query.setParameter(1, reiziger);

        Object adres = query.getSingleResult();

        if (adres instanceof Adres) {
            return (Adres) adres;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Query query = session.createQuery("from Adres ");

        List adresList = query.list();

        if (!adresList.isEmpty() && adresList.get(0) instanceof Adres) {
            return (List<Adres>) adresList;
        }
        return new ArrayList<>();
    }
}
