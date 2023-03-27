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

public class InvoiceDao implements Dao<Invoice, Integer> {
    private static final Logger LOGGER = Logger.getLogger(InvoiceDao.class.getName());

    public static ObservableList<Integer> idSupplierOpt;
    public static ObservableList<Integer> idProductOpt;
    public static ObservableList<Integer> supplierList;

    private final Optional<Connection> connection;
    private static JdbcConnection con;

    public InvoiceDao() {
        this.connection = con.getConnection();
        idSupplierOpt = FXCollections.observableArrayList();
        idProductOpt = FXCollections.observableArrayList();
    }

    public static ObservableList<Integer> getIdSupplierOpt() {
        return idSupplierOpt;
    }

    public static void setIdSupplierOpt(ObservableList<Integer> idSupplierOpt) {
        InvoiceDao.idSupplierOpt = idSupplierOpt;
    }

    public static ObservableList<Integer> getIdProductOpt() {
        return idProductOpt;
    }

    public static void setIdProductOpt(ObservableList<Integer> idProductOpt) {
        InvoiceDao.idProductOpt = idProductOpt;
    }

    @Override
    public Optional<Invoice> get(int id) {
        // Use the connection of the database
        return connection.flatMap(conn -> {
            Optional<Invoice> invoice = Optional.empty();
            String sql = "SELECT * FROM invoice WHERE idInvoice = " + id;

            // Create Statement to execute SQL
            try (Statement statement = conn.createStatement();
                 // ResultSet object is a table of data representing a database result set
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer idInv = resultSet.getInt("idInvoice");
                    Integer idSup = resultSet.getInt("idSupplier");
//                    Integer idProd  = resultSet.getInt("idProduk");
                    String tglJatuhTempo = resultSet.getString("tglJatuhTempo");
                    String tglTransaksi = resultSet.getString("tglTransaksi");
                    Integer jumlah = resultSet.getInt("jumlah");
                    Double total = resultSet.getDouble("total");
                    Double ppn = resultSet.getDouble("ppn");
                    Double grandTotal = resultSet.getDouble("grandTotal");

                    invoice = Optional.of(
                            new Invoice(idInv, idSup, tglJatuhTempo, tglTransaksi, jumlah, total, ppn, grandTotal));

                    LOGGER.log(Level.INFO, "Found {0} in database", invoice.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return invoice;
        });
    }

    public static ObservableList<Integer> getSupplierList() {
        return supplierList;
    }

    @Override
    public Collection<Invoice> getAll() {
        Collection<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoice ORDER BY idInvoice";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer idInv = resultSet.getInt("idInvoice");
                    Integer idSup = resultSet.getInt("idSupplier");
//                    Integer idProd  = resultSet.getInt("idProduk");
                    String tglJatuhTempo = resultSet.getString("tglJatuhTempo");
                    String tglTransaksi = resultSet.getString("tglTransaksi");
                    Integer jumlah = resultSet.getInt("jumlah");
                    Double total = resultSet.getDouble("total");
                    Double ppn = resultSet.getDouble("ppn");
                    Double grandTotal = resultSet.getDouble("grandTotal");

                    Invoice invoice = new Invoice(idInv, idSup, tglJatuhTempo, tglTransaksi, jumlah, total, ppn, grandTotal);

                    invoices.add(invoice);

                    LOGGER.log(Level.INFO, "Found {0} in database", invoice);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return invoices;
    }

    @Override
    public Optional<Integer> add(Invoice invoice) {
        String message = "The invoice to be added should not be null";
        Invoice nonNullCustomer = Objects.requireNonNull(invoice, message);
        String sql = "INSERT INTO "
                + "invoice(idInvoice, idSupplier, tglJatuhTempo, tglTransaksi, jumlah, total, ppn, grandTotal) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {


                statement.setInt(1, nonNullCustomer.getIdInv());
//                statement.setInt(2, nonNullCustomer.getIdProd());
                statement.setInt(2, nonNullCustomer.getIdSup());
                statement.setDate(3, Date.valueOf(nonNullCustomer.getTglJatuhTempo()));
                statement.setDate(4, Date.valueOf(nonNullCustomer.getTglTransaksi()));
                statement.setInt(5, nonNullCustomer.getJumlah());
                statement.setDouble(6, nonNullCustomer.getTotal());
                statement.setDouble(7, nonNullCustomer.getPpn());
                statement.setDouble(8, nonNullCustomer.getGrandTotal());

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
                        new Object[]{nonNullCustomer,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void update(Invoice invoice) {
        String message = "The customer to be updated should not be null";
        Invoice nonNullCustomer = Objects.requireNonNull(invoice, message);
        String sql = "UPDATE invoice "
                + "SET "
//                + "idInvoice = ?, "
                + "idSupplier = ?, "
//                + "idProduk = ? "
                + "tglJatuhTempo = ? "
                + "tglTransaksi = ?, "
                + "jumlah = ? "
                + "total = ?, "
                + "ppn = ? "
                + "grandTotal = ?, "
                + "WHERE "
                + "idInvoice = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullCustomer.getIdInv());
//                statement.setInt(2, nonNullCustomer.getIdProd());
                statement.setInt(2, nonNullCustomer.getIdSup());
                statement.setDate(3, Date.valueOf(nonNullCustomer.getTglJatuhTempo()));
                statement.setDate(4, Date.valueOf(nonNullCustomer.getTglTransaksi()));
                statement.setInt(5, nonNullCustomer.getJumlah());
                statement.setDouble(6, nonNullCustomer.getTotal());
                statement.setDouble(7, nonNullCustomer.getPpn());
                statement.setDouble(8, nonNullCustomer.getGrandTotal());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the invoice updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(int id) {
//        String sql = "DELETE FROM customers WHERE id = ?";
//
//        connection.ifPresent(conn -> {
//            try (PreparedStatement statement = conn.prepareStatement(sql)) {
//
//                statement.setInt(1, id);
//
//                int numberOfDeletedRows = statement.executeUpdate();
//
//                LOGGER.log(Level.INFO, "Was the customer deleted successfully? {0}",
//                        numberOfDeletedRows > 0);
//
//            } catch (SQLException ex) {
//                LOGGER.log(Level.SEVERE, null, ex);
//            }
//        });
    }

    //    @Override
    public void fillComboBox() {
        idSupplierOpt = FXCollections.observableArrayList();
        idProductOpt = FXCollections.observableArrayList();

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("SELECT id_supplier from supplier")) {

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    idSupplierOpt.add(resultSet.getInt("id_supplier"));
                }

                statement.close();
                resultSet.close();


            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}

