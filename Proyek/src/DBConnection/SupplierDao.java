package DBConnection;

import Interface.Dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDao implements Dao<Supplier, Integer> {
    private static final Logger LOGGER = Logger.getLogger(SupplierDao.class.getName());

    private final Optional<Connection> connection;
    private static JdbcConnection con;

    public SupplierDao() {
        this.connection = con.getConnection();
    }

    @Override
    public Optional<Supplier> get(int id) {
        // Use the connection of the database
        return connection.flatMap(conn -> {
            Optional<Supplier> supplier = Optional.empty();
            String sql = "SELECT * FROM supplier WHERE id_supplier = " + id;

            // Create Statement to execute SQL
            try (Statement statement = conn.createStatement();
                 // ResultSet object is a table of data representing a database result set
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String name = resultSet.getString("nameSup");
                    Integer price = resultSet.getInt("priceSup");
                    String email = resultSet.getString("emailSup");
                    String phone = resultSet.getString("phoneNumSup");

                    supplier = Optional.of(
                            new Supplier(id, name, price, email, phone));

                    LOGGER.log(Level.INFO, "Found {0} in database", supplier.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return supplier;
        });
    }

    @Override
    public Collection<Supplier> getAll() {
        Collection<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM supplier ORDER BY id_supplier";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id_supplier");
                    String name = resultSet.getString("nama_supplier");
                    Integer price = resultSet.getInt("harga_jasa");
                    String email = resultSet.getString("email_supplier");
                    String phone = resultSet.getString("telp_supplier");

                    Supplier supplier = new Supplier(id, name, price, email, phone);

                    suppliers.add(supplier);

                    LOGGER.log(Level.INFO, "Found {0} in database", supplier);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return suppliers;
    }

    @Override
    public Optional<Integer> add(Supplier supplier) {
        String message = "The supplier to be added should not be null";
        Supplier nonNullSupplier = Objects.requireNonNull(supplier, message);
        String sql = "INSERT INTO "
                + "supplier(nama_supplier, harga_jasa, email_supplier, telp_supplier) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullSupplier.getNameSup());
                statement.setInt(2, nonNullSupplier.getPriceSup());
                statement.setString(3, nonNullSupplier.getEmailSup());
                statement.setString(4, nonNullSupplier.getPhoneNumSup());

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
                        new Object[]{nonNullSupplier,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }


    @Override
    public void update(Supplier supplier) {
        String message = "The customer to be updated should not be null";
        Supplier nonNullSupplier = Objects.requireNonNull(supplier, message);
        String sql = "UPDATE supplier "
                + "SET "
                + "nama_supplier = ?, "
                + "harga_jasa = ?,"
                + "email_supplier = ?,"
                + "telp_supplier = ?"
                + "WHERE "
                + "id_supplier = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullSupplier.getNameSup());
                statement.setInt(2, nonNullSupplier.getPriceSup());
                statement.setString(3, nonNullSupplier.getEmailSup());
                statement.setString(4, nonNullSupplier.getPhoneNumSup());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the supplier updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM supplier WHERE id_supplier = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, id);

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the supplier deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void fillComboBox(){

    }
}

