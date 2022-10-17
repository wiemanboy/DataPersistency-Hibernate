package data;


import data.interfaces.AdresDAO;
import domain.Adres;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Transaction transaction = session.beginTransaction();

        session.persist(adres);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.merge(adres);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.remove(adres);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Adres where id = ?1", Adres.class);
        query.setParameter(1, id);

        Object adresList = query.getSingleResult();

        transaction.commit();
        if (adresList instanceof Adres) {
            return (Adres) adresList;
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Adres where reiziger = ?1", Adres.class);
        query.setParameter(1, reiziger);

        Object adres = query.getSingleResult();

        transaction.commit();
        if (adres instanceof Adres) {
            return (Adres) adres;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Adres ", Adres.class);

        List adresList = query.list();

        transaction.commit();
        if (!adresList.isEmpty() && adresList.get(0) instanceof Adres) {
            return (List<Adres>) adresList;
        }
        return new ArrayList<>();
    }
}
