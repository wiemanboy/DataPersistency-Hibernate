package data;

import data.interfaces.OVChipkaartDAO;
import domain.OVChipkaart;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.persist(ovChipkaart);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.merge(ovChipkaart);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.remove(ovChipkaart);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from OVChipkaart where reiziger = ?1", OVChipkaart.class);
        query.setParameter(1, reiziger);

        List kaartList = query.list();

        transaction.commit();
        if (!kaartList.isEmpty() && kaartList.get(0) instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from OVChipkaart ", OVChipkaart.class);

        List kaartList = query.list();

        transaction.commit();
        if (!kaartList.isEmpty() && kaartList.get(0) instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OVChipkaart> findById(int id) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from OVChipkaart where id = ?1", OVChipkaart.class);
        query.setParameter(1, id);

        List kaartList = query.list();

        transaction.commit();
        if (kaartList instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }
}
