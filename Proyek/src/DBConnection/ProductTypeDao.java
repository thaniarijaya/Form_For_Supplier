package DBConnection;

import Interface.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductTypeDao implements Dao <ProductType, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ProductTypeDao.class.getName());
    private final Optional<Connection> connection;
    private static JdbcConnection con;
    public static ObservableList<Integer> productTypeOpt;

    public ProductTypeDao() {
        this.connection = con.getConnection();
        productTypeOpt = FXCollections.observableArrayList();
    }

    public static ObservableList<Integer> getSupplierOpt() {
        return productTypeOpt;
    }

    public static void setSupplierOpt(ObservableList<Integer> supplierOpt) {
        ProdukDao.supplierOpt = supplierOpt;
    }


    @Override
    public Optional<ProductType> get(int id) {
        // Use the connection of the database
        return connection.flatMap(conn -> {
            Optional<ProductType> productType = Optional.empty();
            String sql = "SELECT * FROM jenis_produk WHERE id_jenis_produk = " + id;

            // Create Statement to execute SQL
            try (Statement statement = conn.createStatement();
                 // ResultSet object is a table of data representing a database result set
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int idJP = resultSet.getInt("id_jenis_produk");
                    String namaJP = resultSet.getString("nama_jenis_produk");

                    productType = Optional.of(
                            new ProductType(idJP, namaJP));

                    LOGGER.log(Level.INFO, "Found {0} in database", productType.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return productType;
        });
    }

    @Override
    public Collection<ProductType> getAll() {
        Collection<ProductType> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM jenis_produk ORDER BY id_jenis_produk";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int idJP = resultSet.getInt("id_jenis_produk");
                    String nama = resultSet.getString("nama_jenis_produk");

                    ProductType productType = new ProductType(idJP, nama);

                    productTypes.add(productType);

                    LOGGER.log(Level.INFO, "Found {0} in database", productType);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return productTypes;
    }

    @Override
    public Optional<Integer> add(ProductType productType) {
        return Optional.empty();
    }

    @Override
    public void update(ProductType productType) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void fillComboBox() {

    }

}
