package DBConnection;

import Interface.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintInvoiceDao implements Dao<PrintInvoice, Integer>{
    private static final Logger LOGGER = Logger.getLogger(PrintInvoiceDao.class.getName());

    public static ObservableList<Integer> idSupplierOpt;
    public static ObservableList<Integer> idProductOpt;

    private final Optional<Connection> connection;
    private static JdbcConnection con;

    public PrintInvoiceDao() {
        this.connection = con.getConnection();
        idSupplierOpt = FXCollections.observableArrayList();
        idProductOpt = FXCollections.observableArrayList();
    }

    public static ObservableList<Integer> getIdSupplierOpt() {
        return idSupplierOpt;
    }

    public static void setIdSupplierOpt(ObservableList<Integer> idSupplierOpt) {
        PrintInvoiceDao.idSupplierOpt = idSupplierOpt;
    }

    public static ObservableList<Integer> getIdProductOpt() {
        return idProductOpt;
    }

    public static void setIdProductOpt(ObservableList<Integer> idProductOpt) {
        PrintInvoiceDao.idProductOpt = idProductOpt;
    }

    @Override
    public Optional<PrintInvoice> get(int id) {
        // Use the connection of the database
        return connection.flatMap(conn -> {
            Optional<PrintInvoice> printInvoice = Optional.empty();
            String sql = "select inv.idinvoice, sup.id_supplier, prod.id_produk, nama_produk, jumlah, tgltransaksi, tgljatuhtempo, total, ppn, grandtotal\n" +
                    "from invoice inv join supplier sup on (inv.idsupplier = sup.id_supplier) join produk prod on (sup.id_supplier = prod.id_supplier)\n" +
                    "where inv.idinvoice = " + id;
            // Create Statement to execute SQL
            try (Statement statement = conn.createStatement();
                 // ResultSet object is a table of data representing a database result set
                 ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    Integer idInv = resultSet.getInt("idInvoice");
                    Integer idSup = resultSet.getInt("idSupplier");
                    Integer idProd  = resultSet.getInt("idProduk");
                    String namaProd = resultSet.getString("namaProduk");
                    Integer jumlah = resultSet.getInt("jumlah");
                    String tglTransaksi = resultSet.getString("tglTransaksi");
                    String tglJatuhTempo = resultSet.getString("tglJatuhTempo");
                    Double total = resultSet.getDouble("total");
                    Double ppn = resultSet.getDouble("ppn");
                    Double grandTotal = resultSet.getDouble("grandTotal");
                    printInvoice = Optional.of(new PrintInvoice(idInv, idSup, idProd, namaProd, jumlah, tglTransaksi, tglJatuhTempo, total, ppn, grandTotal));
                    LOGGER.log(Level.INFO, "Found {0} in database", printInvoice.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return printInvoice;
        });
    }

    @Override
    public Collection<PrintInvoice> getAll() {
        Collection<PrintInvoice> printInvoices= new ArrayList<>();
        String sql = "select inv.idinvoice, sup.id_supplier, prod.id_produk, nama_produk, jumlah, tgltransaksi, tgljatuhtempo, total, ppn, grandtotal\n" +
                "from invoice inv join supplier sup on (inv.idsupplier = sup.id_supplier) join produk prod on (sup.id_supplier = prod.id_supplier)\n" +
                "order by inv.idinvoice";
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Integer idInv = resultSet.getInt("idInvoice");
                    Integer idSup = resultSet.getInt("idSupplier");
                    Integer idProd  = resultSet.getInt("idProduk");
                    String namaProd = resultSet.getString("namaProduk");
                    Integer jumlah = resultSet.getInt("jumlah");
                    String tglTransaksi = resultSet.getString("tglTransaksi");
                    String tglJatuhTempo = resultSet.getString("tglJatuhTempo");
                    Double total = resultSet.getDouble("total");
                    Double ppn = resultSet.getDouble("ppn");
                    Double grandTotal = resultSet.getDouble("grandTotal");
                    PrintInvoice printInvoice = new PrintInvoice(idInv, idSup, idProd, namaProd, jumlah, tglJatuhTempo, tglTransaksi, total, ppn, grandTotal);
                    printInvoices.add(printInvoice);
                    LOGGER.log(Level.INFO, "Found {0} in database", printInvoice);
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return printInvoices;
    }

    @Override
    public Optional<Integer> add(PrintInvoice printInvoice) {
        return Optional.empty();
    }

    @Override
    public void update(PrintInvoice printInvoice) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void fillComboBox() {
    }
}

