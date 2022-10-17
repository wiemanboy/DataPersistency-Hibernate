import data.AdresDAOHibernate;
import data.OVChipkaartDAOHibernate;
import data.ProductDAOHibernate;
import data.ReizigerDAOHibernate;
import data.interfaces.AdresDAO;
import data.interfaces.OVChipkaartDAO;
import data.interfaces.ProductDAO;
import data.interfaces.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        //testFetchAll();
        testDAO();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testDAO() {
        Session session = getSession();
        Transaction transaction = session.getTransaction();

        try {
            // make DAO
            OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOHibernate(session);
            AdresDAO adresDAO = new AdresDAOHibernate(session);
            ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(session);
            ProductDAO productDAO = new ProductDAOHibernate(session);

            // test DAO
            testReizigerDAO(reizigerDAO);
            testAdresDAO(adresDAO, reizigerDAO);
            testOVChipDAO(ovChipkaartDAO, reizigerDAO);
            testProductDAO(productDAO, reizigerDAO);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("ROLLBACK!!!");
            transaction.rollback();
        }
        finally {
            session.close();
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n----------testReizigerDAO----------");

        Reiziger reiziger = new Reiziger(100,"j", null, "wieman", Date.valueOf("2004-04-17"));

        List<Reiziger> reizigers= rdao.findAll();

        System.out.println("findAll gives:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        System.out.println("\nfindByGbdatum gives:");
        for (Reiziger r : rdao.findByGbdatum("2002-12-03")) {
            System.out.println(r);
        }

        // test save
        System.out.println("\nsave before: " + reizigers.size());
        rdao.save(reiziger);
        reizigers = rdao.findAll();
        System.out.println("after: " + reizigers.size());

        System.out.println("\nupdate before: " + rdao.findById(100));
        reiziger.setVoorletters("jag");
        rdao.update(reiziger);
        System.out.println("update after: " + rdao.findById(100));

        // test delete
        System.out.println("\ndelete before: " + reizigers.size());
        rdao.delete(reiziger);
        reizigers = rdao.findAll();
        System.out.println("after: " + reizigers.size());
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n----------testAdresDAO----------");

        Adres adres = new Adres(100, "1234LK", "3A", "brugweg", "utrecht");
        Reiziger reiziger = new Reiziger(100,"j", null, "wieman", Date.valueOf("2004-04-17"), adres);
        adres.setReiziger(reiziger);

        List<Adres> adresList = adao.findAll();

        System.out.println("\nfindAll gives:");
        for (Adres a : adresList) {
            System.out.println(a);
        }

        // test save
        System.out.println("\nsave before: " + adresList.size());
        rdao.save(reiziger);
        adresList = adao.findAll();
        System.out.println("after: " + adresList.size());

        System.out.println("\nfindById gives: " + adao.findById(reiziger.getId()));
        System.out.println("\nfindByReiziger gives: " + adao.findByReiziger(reiziger));

        // test update
        System.out.println("\nupdate before: " + rdao.findById(100));
        adres.setHuisnummer("3B");
        rdao.update(reiziger);
        System.out.println("update after: " + rdao.findById(100));

        // test delete
        System.out.println("\ndelete before: " + adresList.size());
        rdao.delete(reiziger);
        adresList = adao.findAll();
        System.out.println("delete after: " + adresList.size());
    }

    private static void testOVChipDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n----------testOVChipkaartDAO----------");

        OVChipkaart ovChipkaart = new OVChipkaart(100, Date.valueOf("2004-04-17"), 1, 100);
        OVChipkaart ovChipkaart2 = new OVChipkaart(101, Date.valueOf("2004-04-17"), 1, 100);
        Adres adres = new Adres(100, "1234LK", "3A", "brugweg", "utrecht");
        Reiziger reiziger = new Reiziger(100, "j", null, "wieman", Date.valueOf("2004-04-17"), adres);
        adres.setReiziger(reiziger);
        ovChipkaart.setReiziger(reiziger);
        reiziger.addOVChipkaart(ovChipkaart);

        List<OVChipkaart> kaartList = odao.findAll();

        System.out.println("\nfindAll gives:");
        for (OVChipkaart o : kaartList) {
            System.out.println(o);
        }

        // test save
        System.out.println("\nsave before: " + kaartList.size());
        rdao.save(reiziger);
        kaartList = odao.findAll();
        System.out.println("after: " + kaartList.size());

        System.out.println("\nfindById gives: " + odao.findById(reiziger.getId()));
        System.out.println("\nfindByReiziger gives: " + odao.findByReiziger(reiziger));

        // test update
        System.out.println("\nupdate before: " + rdao.findById(100));
        ovChipkaart.setSaldo(69);
        reiziger.addOVChipkaart(ovChipkaart2);
        ovChipkaart2.setReiziger(reiziger);
        rdao.update(reiziger);
        System.out.println("update after: " + rdao.findById(100));

        // test delete
        System.out.println("\ndelete before: " + kaartList.size());
        rdao.delete(reiziger);
        kaartList = odao.findAll();
        System.out.println("delete after: " + kaartList.size());
    }

    private static void testProductDAO(ProductDAO pdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n----------testProductDAO----------");

        OVChipkaart ovChipkaart = new OVChipkaart(100, Date.valueOf("2004-04-17"), 1, 100);
        OVChipkaart ovChipkaart2 = new OVChipkaart(101, Date.valueOf("2004-04-17"), 1, 100);
        Adres adres = new Adres(100, "1234LK", "3A", "brugweg", "utrecht");
        Reiziger reiziger = new Reiziger(100, "j", null, "wieman", Date.valueOf("2004-04-17"), adres);
        Product product = new Product(100,"test","test",100);
        adres.setReiziger(reiziger);
        ovChipkaart.setReiziger(reiziger);
        ovChipkaart2.setReiziger(reiziger);

        product.addOvChip(ovChipkaart);

        rdao.save(reiziger);

        List<Product> productList = pdao.findAll();

        System.out.println("\nfindAll gives:");
        for (Product p : productList) {
            System.out.println(p);
        }

        // test save
        System.out.println("\nsave before: " + productList.size());
        pdao.save(product);
        productList = pdao.findAll();
        System.out.println("after: " + productList.size());

        System.out.println("\nfindById gives: " + pdao.findById(product.getProductNummer()));
        System.out.println("\nfindByOVChipkaart gives: " + pdao.findByOvChipkaart(ovChipkaart));

        // test update
        System.out.println("\nupdate before: " + pdao.findById(product.getProductNummer()));
        product.addOvChip(ovChipkaart2);
        product.setBeschrijving("tset");
        pdao.update(product);
        System.out.println("update after: " + pdao.findById(product.getProductNummer()));

        // test delete
        System.out.println("\ndelete before: " + productList.size());
        pdao.delete(product);
        productList = pdao.findAll();
        System.out.println("delete after: " + productList.size());

        rdao.delete(reiziger);
    }
}