package DBConnection;

public class ProductType {
    Integer idJenisProd;
    String namaJenisProd;

    public ProductType(Integer idJenisProd, String namaJenisProd) {
        this.idJenisProd = idJenisProd;
        this.namaJenisProd = namaJenisProd;
    }

    public Integer getIdJenisProd() {
        return idJenisProd;
    }

    public void setIdJenisProd(Integer idJenisProd) {
        this.idJenisProd = idJenisProd;
    }

    public String getNamaJenisProd() {
        return namaJenisProd;
    }

    public void setNamaJenisProd(String namaJenisProd) {
        this.namaJenisProd = namaJenisProd;
    }
}
