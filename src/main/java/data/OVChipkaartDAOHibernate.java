package data;

import data.interfaces.OVChipkaartDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;
import org.hibernate.Session;
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
        session.persist(ovChipkaart);
        session.flush();
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        session.merge(ovChipkaart);
        session.flush();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        session.remove(ovChipkaart);
        session.flush();
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        Query query = session.createQuery("from OVChipkaart where reiziger = ?1");
        query.setParameter(1, reiziger);

        List kaartList = query.list();

        if (!kaartList.isEmpty() && kaartList.get(0) instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        Query query = session.createQuery("from OVChipkaart ");

        List kaartList = query.list();

        if (!kaartList.isEmpty() && kaartList.get(0) instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OVChipkaart> findById(int id) throws SQLException {
        Query query = session.createQuery("from OVChipkaart where id = ?1");
        query.setParameter(1, id);

        List kaartList = query.list();

        if (kaartList instanceof OVChipkaart) {
            return (List<OVChipkaart>) kaartList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OVChipkaart> findByProduct(int id) throws SQLException {
        return null;
    }
}
