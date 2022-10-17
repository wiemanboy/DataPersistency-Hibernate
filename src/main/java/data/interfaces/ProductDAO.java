package data.interfaces;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findAll() throws SQLException;
    Product findById(int id) throws SQLException;
    List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException;
}
