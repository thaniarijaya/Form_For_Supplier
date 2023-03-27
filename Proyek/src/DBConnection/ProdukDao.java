package DBConnection;

import Interface.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdukDao implements Dao<Produk, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ProdukDao.class.getName());
    private final Optional<Connection> connection;
    private static JdbcConnection con;
    public static ObservableList<Integer> supplierOpt;
    public static ObservableList<Integer> jenisProduk;


    public ProdukDao() {
        this.connection = con.getConnection();
        supplierOpt = FXCollections.observableArrayList();
        jenisProduk = FXCollections.observableArrayList();
    }

    public static ObservableList<Integer> getSupplierOpt() {
        return supplierOpt;
    }

    public static ObservableList<Integer> getJenisProduk() {
        return jenisProduk;
    }

    public static void setJenisProduk(ObservableList<Integer> jenisProduk) {
        ProdukDao.jenisProduk = jenisProduk;
    }

    @Override
    public Optional<Produk> get(int id) {
        // Use the connection of the database
        return connection.flatMap(conn -> {
            Optional<Produk> produk = Optional.empty();
            String sql = "SELECT * FROM produk WHERE id_produk = " + id;

            // Create Statement to execute SQL
            try (Statement statement = conn.createStatement();
                 // ResultSet object is a table of data representing a database result set
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int id_prod = resultSet.getInt("id_produk");
                    String nama = resultSet.getString("nama_produk");
                    int jumlah = resultSet.getInt("jumlah_produk");
                    int harga = resultSet.getInt("harga_produk");
                    int id_jenis = resultSet.getInt("id_jenis_produk");
                    int id_supplier = resultSet.getInt("id_supplier");

                    produk = Optional.of(
                            new Produk(id_prod, nama, jumlah, harga, id_jenis, id_supplier));

                    LOGGER.log(Level.INFO, "Found {0} in database", produk.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return produk;
        });
    }

    @Override
    public Collection<Produk> getAll() {
        Collection<Produk> products = new ArrayList<>();
        String sql = "SELECT * FROM produk ORDER BY id_produk";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id_prod = resultSet.getInt("id_produk");
                    String nama = resultSet.getString("nama_produk");
                    int jumlah = resultSet.getInt("jumlah_produk");
                    int harga = resultSet.getInt("harga_produk");
                    int id_jenis = resultSet.getInt("id_jenis_produk");
                    int id_supplier = resultSet.getInt("id_supplier");

                    Produk produk = new Produk(id_prod, nama, jumlah, harga, id_jenis, id_supplier);

                    products.add(produk);

                    LOGGER.log(Level.INFO, "Found {0} in database", produk);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return products;
    }

    @Override
    public Optional<Integer> add(Produk produk) {
        String message = "The product to be added should not be null";
        Produk nonNullProduk = Objects.requireNonNull(produk, message);
        String sql = "INSERT INTO "
                + "produk(nama_produk, jumlah_produk, harga_produk, id_jenis_produk, id_supplier) "
                + "VALUES(?, ?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullProduk.getNamaProd());
                statement.setInt(2, nonNullProduk.getJumlah());
                statement.setInt(3, nonNullProduk.getHargaProd());
                statement.setInt(4, nonNullProduk.getIdJenis());
                statement.setInt(5, nonNullProduk.getIdSup());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }
                LOGGER.log(
                        Level.INFO,
                        "{0} created successfully? {1}",
                        new Object[]{nonNullProduk,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void update(Produk produk) {
        String message = "The product to be updated should not be null";
        Produk nonNullProduk = Objects.requireNonNull(produk, message);
        String sql = "UPDATE customers "
                + "SET "
                + "nama_produk = ?, "
                + "jumlah_produk = ?, "
                + "harga_produk = ?, "
                + "id_jenis_produk = ?, "
                + "id_supplier = ? "
                + "WHERE "
                + "id_produk = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullProduk.getNamaProd());
                statement.setInt(2, nonNullProduk.getJumlah());
                statement.setInt(3, nonNullProduk.getHargaProd());
                statement.setInt(4, nonNullProduk.getIdJenis());
                statement.setInt(5, nonNullProduk.getIdSup());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the product updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM produk WHERE id_produk = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, id);

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the product deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void fillComboBox(){
        supplierOpt = FXCollections.observableArrayList();
        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("SELECT id_supplier from supplier")){

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    supplierOpt.add(resultSet.getInt("id_supplier"));
                }

                statement.close();
                resultSet.close();


            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("SELECT id_jenis_produk from jenis_produk")){

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    jenisProduk.add(resultSet.getInt("id_jenis_produk"));
                }

                statement.close();
                resultSet.close();


            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

    }
}
