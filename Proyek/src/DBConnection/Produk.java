package DBConnection;

public class Produk {
    int id;
    String namaProd;
    int jumlah;
    int hargaProd;
    int idJenis;
    int idSup;

    public Produk(int id, String namaProd, int jumlah, int hargaProd, int idJenis, int idSup) {
        this.id = id;
        this.namaProd = namaProd;
        this.jumlah = jumlah;
        this.hargaProd = hargaProd;
        this.idJenis = idJenis;
        this.idSup = idSup;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaProd() {
        return namaProd;
    }

    public void setNamaProd(String namaProd) {
        this.namaProd = namaProd;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHargaProd() {
        return hargaProd;
    }

    public void setHargaProd(int hargaProd) {
        this.hargaProd = hargaProd;
    }

    public int getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(int idJenis) {
        this.idJenis = idJenis;
    }

    public int getIdSup() {
        return idSup;
    }

    public void setIdSup(int idSup) {
        this.idSup = idSup;
    }
}


