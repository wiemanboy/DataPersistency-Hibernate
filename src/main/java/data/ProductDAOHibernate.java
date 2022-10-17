package data;

import data.interfaces.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Transaction transaction = session.beginTransaction();

        session.persist(product);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.merge(product);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        Transaction transaction = session.beginTransaction();

        session.remove(product);
        session.flush();

        transaction.commit();
        return true;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Product", Product.class);

        List productList = query.list();

        transaction.commit();
        if (!productList.isEmpty() && productList.get(0) instanceof Product) {
            return (List<Product>) productList;
        }
        return new ArrayList<>();
    }

    @Override
    public Product findById(int id) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Product where productNummer = ?1", Product.class);
        query.setParameter(1, id);

        Object product = query.getSingleResult();

        transaction.commit();
        if (product instanceof Product) {
            return (Product) product;
        }
        return null;
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Product p join p.ovChipkaartList o where o.kaartNummer = :kaartNummer", Product.class);
        query.setParameter("kaartNummer",ovChipkaart.getKaartNummer());

        List productList = query.list();

        transaction.commit();
        if (!productList.isEmpty() && productList.get(0) instanceof Product) {
            return (List<Product>) productList;
        }
        return new ArrayList<>();
    }
}
