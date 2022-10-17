package data;

import data.interfaces.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        session.persist(product);
        session.flush();
        return true;    }

    @Override
    public boolean update(Product product) throws SQLException {
        session.merge(product);
        session.flush();
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        session.remove(product);
        session.flush();
        return true;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Query query = session.createQuery("from Product");

        List productList = query.list();

        if (!productList.isEmpty() && productList.get(0) instanceof Product) {
            return (List<Product>) productList;
        }
        return new ArrayList<>();
    }

    @Override
    public Product findById(int id) throws SQLException {
        Query query = session.createQuery("from Product where productNummer = ?1");
        query.setParameter(1, id);

        Object product = query.getSingleResult();

        if (product instanceof Product) {
            return (Product) product;
        }
        return null;
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        Query query = session.createQuery("from Product where ?1 in ovChipkaartList");
        query.setParameter(1,ovChipkaart);

        List productList = query.list();

        if (!productList.isEmpty() && productList.get(0) instanceof Product) {
            return (List<Product>) productList;
        }
        return new ArrayList<>();
    }
}
