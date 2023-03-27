package sample;

import DBConnection.*;
import Interface.Dao;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView<Supplier> tableViewSupplier; // <Object>
    @FXML
    private TableView<Produk> tableViewProds;
    @FXML
    private TableView<ProductType> tableViewProdTypes;
    @FXML
    private TableColumn<Supplier, Integer> colID; // <Object, DataType>
    @FXML
    private TableColumn<Produk, Integer> colIdProd;
    @FXML
    private TableColumn<Supplier, String> colName;
    @FXML
    private TableColumn<Produk, String> colNamaProd;
    @FXML
    private TableColumn<Produk, Integer> colJumlah;
    @FXML
    private TableColumn<Supplier, Integer> colPrice; //harga jasa supplier
    @FXML
    private TableColumn<Produk, Integer> colHarga; //harga produk
    @FXML
    private TableColumn<Supplier, String> colPhoneNum;
    @FXML
    private TableColumn<Supplier, String> colEmail;
    @FXML
    private TableColumn<Produk, Integer> colIdJenis;
    @FXML
    private TableColumn<ProductType, Integer> colIDJP;
    @FXML
    private TableColumn<ProductType, String> colNamaJP;
    @FXML
    private TableColumn<Produk, Integer> colIdSup;
    @FXML
    private TextField txtNama, txtHarga, txtTelp, txtEmail, prodID, prodNama, prodHarga, prodJumlah;
    @FXML
    private TextField txtIDinvoice;
    @FXML
    private TextField jumlah;
    @FXML
    private TextField total;
    @FXML
    private TextField ppn;
    @FXML
    private TextField grandTotal;
    @FXML
    private DatePicker tglJatuhTempo;
    @FXML
    private DatePicker tglTransaksi;
    @FXML
    private ComboBox <Integer>idSupplier;
    @FXML
    private Button addInv;
    @FXML
    private ComboBox jenisProd, supply;
    @FXML
    private TextField search;
    @FXML
    private TextArea print;
    @FXML
    private Button searchId;
    @FXML
    private TextField txt;

    private static final Dao<Supplier, Integer> sup = new SupplierDao();
    private static final Dao<Produk, Integer> prod = new ProdukDao();
    private static final Dao<ProductType, Integer> prodType = new ProductTypeDao();
    private static final Dao<Invoice, Integer> inv = new InvoiceDao();

    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    private ObservableList<Produk> products = FXCollections.observableArrayList();
    private ObservableList<ProductType> productTypes = FXCollections.observableArrayList();

    private Integer index;


    // Add new data using CustomerDao add(Customer customer)
    @FXML
    void addSupplier(ActionEvent event) {
        Supplier supplier = new Supplier(0, txtNama.getText(), Integer.valueOf(txtHarga.getText()),
                txtEmail.getText(), txtTelp.getText());

        sup.add(supplier).ifPresent(supplier::setIdSup);
        viewAllRecord();
        refreshComboBox();

        txtNama.clear();
        txtHarga.clear();
        txtEmail.clear();
        txtTelp.clear();
    }

    @FXML
    void addInvoice(ActionEvent event) {
        Invoice invoice = new Invoice(Integer.parseInt(txtIDinvoice.getText()),Integer.parseInt(idSupplier.getId()),tglJatuhTempo.getValue().toString(),tglTransaksi.getValue().toString(),Integer.parseInt(jumlah.getText()),Double.parseDouble(total.getText()),Double.parseDouble(ppn.getText()),Double.parseDouble(grandTotal.getText()));
        inv.add(invoice).ifPresent(invoice::setIdInv);
        txt.setText("Input berhasil!");
        refreshComboBox();
        txtIDinvoice.clear();
        jumlah.clear();
        total.clear();
        ppn.clear();
        grandTotal.clear();
        tglJatuhTempo.getEditor().clear();
        tglTransaksi.getEditor().clear();
        idSupplier.getEditor().clear();
    }

    @FXML
    void searchInvoice(ActionEvent event){
        PrintInvoice printInvoice = new PrintInvoice(Integer.parseInt(txtIDinvoice.getText()), Integer.parseInt(idSupplier.getId()), Integer.parseInt(prodID.getId()), prodNama.getText(), Integer.parseInt(prodJumlah.getText()),tglJatuhTempo.getValue().toString(),tglTransaksi.getValue().toString(), Double.parseDouble(total.getText()), Double.parseDouble(ppn.getText()), Double.parseDouble(grandTotal.getText()));
        if (search.getText() == txtIDinvoice.getText()){
            print.appendText("ID Invoice: " + txtIDinvoice.getText());
            print.appendText("\n");
            print.appendText(idSupplier.getId());
            print.appendText("\n");
            print.appendText(prodID.getId());
            print.appendText("\n");
            print.appendText(prodNama.getText());
            print.appendText("\n");
            print.appendText(prodJumlah.getText());
            print.appendText("\n");
            print.appendText(String.valueOf(tglTransaksi.getValue()));
            print.appendText("\n");
            print.appendText(String.valueOf(tglJatuhTempo.getValue()));
            print.appendText("\n");
            print.appendText(total.getText());
            print.appendText("\n");
            print.appendText(ppn.getText());
            print.appendText("\n");
            print.appendText(grandTotal.getText());
        }

    }

    @FXML
    void addProduct(ActionEvent event) {
        //Integer jenis = jenisProd.getSelectionModel().getSelectedItem();
        //Integer supp = supply.getSelectionModel().getSelectedItem();

        Produk produk = new Produk(0, prodNama.getText(),
                Integer.parseInt(prodJumlah.getText()),
                Integer.parseInt(prodHarga.getText()),
                Integer.parseInt((String) jenisProd.getValue()),
                Integer.parseInt((String) supply.getValue()));


        prod.add(produk).ifPresent(produk::setId);
        viewAllProducts();

        prodNama.clear();
        prodHarga.clear();
        prodJumlah.clear();

    }

    @FXML
    void getSelected(MouseEvent event) {
        // Get Index of the Selected Row
        index = tableViewSupplier.getSelectionModel().getSelectedIndex();
        // Out of bound checking
        if (index <= -1) {
            return;
        }

        // Fill textField with Supplier Data
        txtNama.setText(colName.getCellData(index));
        txtHarga.setText(colPrice.getCellData(index).toString());
        txtEmail.setText(colEmail.getCellData(index));
        txtTelp.setText(colPhoneNum.getCellData(index));
    }

    @FXML
    void getSelectedProduct(MouseEvent event) {
        // Get Index of the Selected Row
        index = tableViewProds.getSelectionModel().getSelectedIndex();
        // Out of bound checking
        if (index <= -1) {
            return;
        }

        // Fill textField with Supplier Data
        prodID.setText(colIdProd.getCellData(index).toString());
        prodNama.setText(colNamaProd.getCellData(index));
        prodJumlah.setText(colJumlah.getCellData(index).toString());
        prodHarga.setText(colHarga.getCellData(index).toString());
        jenisProd.setValue(colIdJenis.getCellData(index));
        supply.setValue(colIdSup.getCellData(index));
    }


    @FXML
        // Update selected row data using Supplier update(Customer customer)
    void updateSupplier(ActionEvent event) {
        // Update by id, value from TextView
        Supplier supplier = new Supplier(colID.getCellData(index), txtNama.getText(),
                Integer.valueOf(txtHarga.getText()), txtEmail.getText(),
                txtTelp.getText());
        sup.update(supplier);
        viewAllRecord();

        txtNama.clear();
        txtHarga.clear();
        txtEmail.clear();
        txtTelp.clear();
    }

    @FXML
        // Update selected row data using Supplier update(Customer customer)
    void updateProduct(ActionEvent event) {
        // Update by id, value from TextView
        Produk produk = new Produk(colIdProd.getCellData(index), prodNama.getText(),
                Integer.parseInt(prodJumlah.getText()), Integer.parseInt(prodHarga.getText()),
                Integer.parseInt((String) jenisProd.getValue()),
                Integer.parseInt((String) supply.getValue()));
        prod.update(produk);
        viewAllProducts();

        prodID.clear();
        prodNama.clear();
        prodHarga.clear();
        prodJumlah.clear();
    }

    @FXML
        // Delete selected row data using SupplierDao delete(int id)
    void deleteSupplier(ActionEvent event) {
        // Delete by id
        sup.delete(colID.getCellData(index));
        viewAllRecord();
        refreshComboBox();

        txtNama.clear();
        txtHarga.clear();
        txtEmail.clear();
        txtTelp.clear();
    }

    @FXML
        // Delete selected row data using CustomerDao delete(int id)
    void deleteProduct(ActionEvent event) {
        // Delete by id
        prod.delete(colIdProd.getCellData(index));
        viewAllProducts();

        prodID.clear();
        prodNama.clear();
        prodHarga.clear();
        prodJumlah.clear();
    }


    // Get table data using getAll()
    void viewAllRecord() {
        // Set all data from getAll into ObservableList<Customer>
        suppliers.setAll(sup.getAll());
        tableViewSupplier.setItems(suppliers);
    }

    void viewAllProducts() {
        // Set all data from getAll into ObservableList<Customer>
        products.setAll(prod.getAll());
        tableViewProds.setItems(products);
    }

    void refreshComboBox(){
        prod.fillComboBox();
        inv.fillComboBox();
        supply.setItems(ProdukDao.getSupplierOpt().sorted());
        idSupplier.setItems(InvoiceDao.getIdSupplierOpt().sorted());
    }

    void viewAllProductTypes() {
        // Set all data from getAll into ObservableList<Customer>
        productTypes.setAll(prodType.getAll());
        tableViewProdTypes.setItems(productTypes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Table Column for Supplier
        colID.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("IdSup"));
        colName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("NameSup"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("PriceSup"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Supplier, String>("EmailSup"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory<Supplier, String>("PhoneNumSup"));

        // Initialize Table Column for Produk
        colIdProd.setCellValueFactory(new PropertyValueFactory<Produk, Integer>("Id"));
        colNamaProd.setCellValueFactory(new PropertyValueFactory<Produk, String>("NamaProd"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<Produk, Integer>("Jumlah"));
        colHarga.setCellValueFactory(new PropertyValueFactory<Produk, Integer>("HargaProd"));
        colIdJenis.setCellValueFactory(new PropertyValueFactory<Produk, Integer>("IdJenis"));
        colIdSup.setCellValueFactory(new PropertyValueFactory<Produk, Integer>("IdSup"));

        //Jenis Produk
        colIDJP.setCellValueFactory(new PropertyValueFactory<ProductType, Integer>("IdJenisProd"));
        colNamaJP.setCellValueFactory(new PropertyValueFactory<ProductType, String>("NamaJenisProd"));

        jenisProd.setItems(ProdukDao.getJenisProduk());
        refreshComboBox();

        idSupplier.setItems(InvoiceDao.getSupplierList());
        refreshComboBox();


        viewAllRecord();
        viewAllProducts();
        viewAllProductTypes();
    }
}
